// File: MultiThreadedBankSimulator.java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

/**
 * MultiThreadedBankSimulator
 * Single-file simulation demonstrating:
 * - ExecutorService, Callable/Runnable
 * - ReentrantLock per Account, tryLock with timeout to avoid deadlocks
 * - ScheduledExecutorService for periodic interest
 * - ConcurrentLinkedQueue for transaction logging
 * - Graceful shutdown and metrics
 *
 * Compile: javac MultiThreadedBankSimulator.java
 * Run:     java MultiThreadedBankSimulator
 */
public class MultiThreadedBankSimulator {

    /* ---------- Exceptions ---------- */
    static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String msg) { super(msg); }
    }

    /* ---------- Domain classes ---------- */
    static class Account implements Comparable<Account> {
        private final String accountId;
        private final String ownerName;
        private long balance; // in cents to avoid floating issues
        private final ReentrantLock lock = new ReentrantLock();

        public Account(String accountId, String ownerName, long initialBalanceCents) {
            this.accountId = accountId;
            this.ownerName = ownerName;
            this.balance = initialBalanceCents;
        }

        public String getAccountId() { return accountId; }
        public String getOwnerName() { return ownerName; }

        // deposit and withdraw must be thread-safe
        public void deposit(long amountCents) {
            lock.lock();
            try {
                balance += amountCents;
            } finally {
                lock.unlock();
            }
        }

        public void withdraw(long amountCents) throws InsufficientFundsException {
            lock.lock();
            try {
                if (balance < amountCents) {
                    throw new InsufficientFundsException("Account " + accountId + " has insufficient funds.");
                }
                balance -= amountCents;
            } finally {
                lock.unlock();
            }
        }

        public long getBalance() {
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }

        public ReentrantLock getLock() { return lock; }

        // transferTo attempts to transfer amount from this -> target
        // We'll acquire locks in a global order to avoid deadlocks.
        public void transferTo(Account target, long amountCents) throws InsufficientFundsException, InterruptedException {
            // lock ordering by accountId to avoid deadlock
            Account first = this.compareTo(target) <= 0 ? this : target;
            Account second = this.compareTo(target) <= 0 ? target : this;

            // Using tryLock with timeout - if unable to acquire, throw InterruptedException for retry/rollback
            if (first.getLock().tryLock(500, TimeUnit.MILLISECONDS)) {
                try {
                    if (second.getLock().tryLock(500, TimeUnit.MILLISECONDS)) {
                        try {
                            // proceed transfer: withdraw from source, deposit to target
                            if (this.balance < amountCents) {
                                throw new InsufficientFundsException("Insufficient funds in " + accountId);
                            }
                            this.balance -= amountCents;
                            target.balance += amountCents;
                        } finally {
                            second.getLock().unlock();
                        }
                    } else {
                        throw new InterruptedException("Could not acquire second lock for transfer: " + accountId + "->" + target.accountId);
                    }
                } finally {
                    first.getLock().unlock();
                }
            } else {
                throw new InterruptedException("Could not acquire first lock for transfer: " + accountId + "->" + target.accountId);
            }
        }

        @Override
        public int compareTo(Account o) {
            return this.accountId.compareTo(o.accountId);
        }

        @Override
        public String toString() {
            return String.format("Account[id=%s, owner=%s, balance=%.2f]", accountId, ownerName, balance/100.0);
        }
    }

    static class Transaction {
        enum Type { DEPOSIT, WITHDRAW, TRANSFER }
        final Instant timestamp = Instant.now();
        final Type type;
        final String fromAccount; // for deposit, fromAccount = null or "EXTERNAL"
        final String toAccount;   // for withdraw, toAccount = null or "EXTERNAL"
        final long amountCents;
        final boolean success;
        final String message;

        public Transaction(Type type, String fromAccount, String toAccount, long amountCents, boolean success, String message) {
            this.type = type;
            this.fromAccount = fromAccount;
            this.toAccount = toAccount;
            this.amountCents = amountCents;
            this.success = success;
            this.message = message;
        }

        @Override
        public String toString() {
            return String.format("%s | %s | from=%s -> to=%s | %.2f | %s | %s",
                timestamp.toString(), type, fromAccount, toAccount, amountCents/100.0, success ? "SUCCESS" : "FAILED", message);
        }
    }

    /* ---------- Logger thread (consumer) ---------- */
    static class TransactionLogger implements Runnable {
        private final BlockingQueue<Transaction> queue;
        private final String logFile;
        private volatile boolean running = true;

        public TransactionLogger(BlockingQueue<Transaction> queue, String logFile) {
            this.queue = queue;
            this.logFile = logFile;
        }

        public void shutdown() { running = false; }

        @Override
        public void run() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
                while (running || !queue.isEmpty()) {
                    Transaction tx = queue.poll(500, TimeUnit.MILLISECONDS);
                    if (tx != null) {
                        bw.write(tx.toString());
                        bw.newLine();
                    }
                }
                bw.flush();
            } catch (IOException | InterruptedException e) {
                System.err.println("Logger error: " + e.getMessage());
            }
            System.out.println("TransactionLogger stopped.");
        }
    }

    /* ---------- Worker tasks ---------- */

    // ATM worker: randomly deposit or withdraw on random accounts
    static class ATMWorker implements Callable<Integer> {
        private final List<Account> accounts;
        private final BlockingQueue<Transaction> logQueue;
        private final Random rng = new Random();
        private final AtomicInteger successCounter;
        private final AtomicInteger failCounter;
        private final int operationsToPerform;

        public ATMWorker(List<Account> accounts, BlockingQueue<Transaction> logQueue,
                         AtomicInteger successCounter, AtomicInteger failCounter, int operations) {
            this.accounts = accounts;
            this.logQueue = logQueue;
            this.successCounter = successCounter;
            this.failCounter = failCounter;
            this.operationsToPerform = operations;
        }

        @Override
        public Integer call() {
            for (int i = 0; i < operationsToPerform; i++) {
                Account acc = accounts.get(rng.nextInt(accounts.size()));
                boolean deposit = rng.nextBoolean();
                long amountCents = (rng.nextInt(50) + 1) * 100; // 1.00 - 50.00
                if (deposit) {
                    acc.deposit(amountCents);
                    logQueue.offer(new Transaction(Transaction.Type.DEPOSIT, "EXTERNAL", acc.getAccountId(), amountCents, true, "ATM deposit"));
                    successCounter.incrementAndGet();
                } else {
                    try {
                        acc.withdraw(amountCents);
                        logQueue.offer(new Transaction(Transaction.Type.WITHDRAW, acc.getAccountId(), "EXTERNAL", amountCents, true, "ATM withdraw"));
                        successCounter.incrementAndGet();
                    } catch (InsufficientFundsException e) {
                        logQueue.offer(new Transaction(Transaction.Type.WITHDRAW, acc.getAccountId(), "EXTERNAL", amountCents, false, e.getMessage()));
                        failCounter.incrementAndGet();
                    }
                }
                // small random delay
                try { Thread.sleep(rng.nextInt(20)); } catch (InterruptedException ignored) {}
            }
            return operationsToPerform;
        }
    }

    // TransferWorker: performs transfers between random accounts
    static class TransferWorker implements Callable<Integer> {
        private final List<Account> accounts;
        private final BlockingQueue<Transaction> logQueue;
        private final Random rng = new Random();
        private final AtomicInteger successCounter;
        private final AtomicInteger failCounter;
        private final int operationsToPerform;

        public TransferWorker(List<Account> accounts, BlockingQueue<Transaction> logQueue,
                              AtomicInteger successCounter, AtomicInteger failCounter, int operations) {
            this.accounts = accounts;
            this.logQueue = logQueue;
            this.successCounter = successCounter;
            this.failCounter = failCounter;
            this.operationsToPerform = operations;
        }

        @Override
        public Integer call() {
            for (int i = 0; i < operationsToPerform; i++) {
                Account a = accounts.get(rng.nextInt(accounts.size()));
                Account b = accounts.get(rng.nextInt(accounts.size()));
                if (a == b) { i--; continue; } // pick different accounts
                long amountCents = (rng.nextInt(30) + 1) * 100;
                try {
                    a.transferTo(b, amountCents);
                    logQueue.offer(new Transaction(Transaction.Type.TRANSFER, a.getAccountId(), b.getAccountId(), amountCents, true, "Transfer"));
                    successCounter.incrementAndGet();
                } catch (InsufficientFundsException e) {
                    logQueue.offer(new Transaction(Transaction.Type.TRANSFER, a.getAccountId(), b.getAccountId(), amountCents, false, e.getMessage()));
                    failCounter.incrementAndGet();
                } catch (InterruptedException e) {
                    logQueue.offer(new Transaction(Transaction.Type.TRANSFER, a.getAccountId(), b.getAccountId(), amountCents, false, "Lock acquisition failed"));
                    failCounter.incrementAndGet();
                }
                try { Thread.sleep(rng.nextInt(30)); } catch (InterruptedException ignored) {}
            }
            return operationsToPerform;
        }
    }

    /* ---------- Main driver ---------- */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting MultiThreadedBankSimulator...");

        // Simulation parameters
        final int NUM_ACCOUNTS = 6;
        final int INITIAL_BALANCE_CENTS = 10_000 * 100 / 100; // 10_000.00 -> here simpler small amounts
        final int ATM_WORKERS = 4;
        final int TRANSFER_WORKERS = 3;
        final int OPERATIONS_PER_WORKER = 200;
        final int INTEREST_PERIOD_SECONDS = 10;
        final double INTEREST_RATE = 0.001; // 0.1% per tick
        final String LOG_FILE = "transactions.log";

        // Counters and data structures
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger failCounter = new AtomicInteger(0);
        BlockingQueue<Transaction> logQueue = new LinkedBlockingQueue<>();
        List<Account> accounts = new ArrayList<>();

        // Create accounts
        for (int i = 1; i <= NUM_ACCOUNTS; i++) {
            // For demonstration, give different balances
            accounts.add(new Account(String.format("A%03d", i), "User" + i, (5_000 + i * 500) * 100L)); // amounts in cents
        }

        // Start logger thread
        TransactionLogger txLogger = new TransactionLogger(new LinkedBlockingQueue<>()); // placeholder
        // We'll use a LinkedBlockingQueue referenced by both actors and logger: reuse logQueue
        txLogger = new TransactionLogger((BlockingQueue<Transaction>) logQueue, LOG_FILE);
        Thread loggerThread = new Thread(txLogger, "TxLogger");
        loggerThread.start();

        // Executor for workers
        ExecutorService workerPool = Executors.newFixedThreadPool(ATM_WORKERS + TRANSFER_WORKERS);

        // Submit ATM workers
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < ATM_WORKERS; i++) {
            futures.add(workerPool.submit(new ATMWorker(accounts, logQueue, successCounter, failCounter, OPERATIONS_PER_WORKER)));
        }

        // Submit Transfer workers
        for (int i = 0; i < TRANSFER_WORKERS; i++) {
            futures.add(workerPool.submit(new TransferWorker(accounts, logQueue, successCounter, failCounter, OPERATIONS_PER_WORKER)));
        }

        // Scheduled interest applicator
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable interestTask = () -> {
            for (Account acc : accounts) {
                // apply small interest by locking account temporarily
                acc.getLock().lock();
                try {
                    long oldBal = acc.getBalance();
                    long interest = Math.round(oldBal * INTEREST_RATE);
                    acc.deposit(interest);
                    logQueue.offer(new Transaction(Transaction.Type.DEPOSIT, "BANK_INTEREST", acc.getAccountId(), interest, true, "Interest applied"));
                } finally {
                    acc.getLock().unlock();
                }
            }
            System.out.println("[Interest] Applied interest to all accounts.");
        };
        scheduler.scheduleAtFixedRate(interestTask, INTEREST_PERIOD_SECONDS, INTEREST_PERIOD_SECONDS, TimeUnit.SECONDS);

        // Wait for workers to finish
        for (Future<Integer> f : futures) {
            try {
                f.get(); // wait for each worker
            } catch (ExecutionException e) {
                System.err.println("Worker threw exception: " + e.getMessage());
            }
        }

        // No more tasks: shutdown worker pool
        workerPool.shutdown();
        workerPool.awaitTermination(5, TimeUnit.SECONDS);

        // Stop interest scheduler
        scheduler.shutdown();
        scheduler.awaitTermination(3, TimeUnit.SECONDS);

        // Allow some time for logger to process queued transactions
        System.out.println("All workers finished. Signalling logger to stop after flushing queue...");
        txLogger.shutdown();

        // Ensure logger thread terminates
        loggerThread.join(5000);

        // Print final summary
        System.out.println("\n=== Simulation Summary ===");
        for (Account acc : accounts) {
            System.out.printf("%s : Balance = %.2f%n", acc.getAccountId(), acc.getBalance()/100.0);
        }
        System.out.printf("Successful transactions: %d%n", successCounter.get());
        System.out.printf("Failed transactions: %d%n", failCounter.get());
        System.out.println("Transactions logged to file: " + LOG_FILE);
        System.out.println("Simulation complete.");
    }
}
  
