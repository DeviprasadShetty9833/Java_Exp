// catalog/Product.java

package catalog;

public class Product {
    private int id;
    protected String name;
    private double price;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void displayDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
    }
}

// catalog/electronics/Electronics.java

package catalog.electronics;

import catalog.Product;

public class Electronics extends Product {
    private String brand;
    private int warranty;

    public Electronics(int id, String name, double price, String brand, int warranty) {
        setId(id);
        this.name = name;  // protected access
        setPrice(price);
        this.brand = brand;
        this.warranty = warranty;
    }

    @Override
    public void displayDetails() {
        System.out.println("--- Electronics ---");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + name);
        System.out.println("Brand: " + brand);
        System.out.println("Warranty: " + warranty + " years");
        System.out.println("Price: " + getPrice());
        System.out.println();
    }
}

// catalog/clothing/Clothing.java

package catalog.clothing;

import catalog.Product;

public class Clothing extends Product {
    private String size;
    private String material;

    public Clothing(int id, String name, double price, String size, String material) {
        setId(id);
        this.name = name;
        setPrice(price);
        this.size = size;
        this.material = material;
    }

    @Override
    public void displayDetails() {
        System.out.println("--- Clothing ---");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + name);
        System.out.println("Size: " + size);
        System.out.println("Material: " + material);
        System.out.println("Price: " + getPrice());
        System.out.println();
    }
}

// app/MainApp.java

package app;

import catalog.electronics.Electronics;
import catalog.clothing.Clothing;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Product Details:\n");

        Electronics laptop = new Electronics(101, "Laptop", 55000.0, "Dell", 2);
        Clothing jacket = new Clothing(202, "Jacket", 3200.0, "L", "Leather");

        laptop.displayDetails();
        jacket.displayDetails();
    }
}
