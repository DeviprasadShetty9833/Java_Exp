Experiment No. 10
Title:
Java JDK 11+
Basic Spring Boot knowledge (controllers, dependencies)
HTML/CSS basics
Develop a form submission app with Spring MVC using Thymeleaf templates
After this experiment students will be able to:
 Use Thymeleaf for server-side HTML templating and binding form fields to model
attributes.
 Implement controller methods for GET (show form) and POST (process form).
 Validate form input and display field-level errors in the form.
 Apply web best practices: Redirect-After-Post and flash messages.
 (Optional) Save and list submitted records from a database.
Create a Spring Boot project with Spring Web and Thymeleaf.
Build a web form (student registration) and render it with Thymeleaf templates.
Perform server-side validation using javax.validation annotations.
Handle form submission, validation errors, and success messages (flash attributes).
(Optional) Persist submitted data using Spring Data JPA + H2.
IDE (IntelliJ, Eclipse, VS Code)
Maven or Gradle
Dependencies: Spring Web, Thymeleaf, Spring Boot Starter Validation, (optional) Spring
Data JPA + H2
To build a Spring MVC web application that accepts user input through HTML forms rendered by
Thymeleaf, performs server-side validation, persists data (optional), and demonstrates common
web patterns (GET form, POST processing, Redirect-After-Post, flash messages).
Objectives
Aim
Outcomes
Prerequisite
Requirements











Deviprasad Shetty, 55
Project Setup
UseSpringInitializr(https://start.spring.io):
Brief Theory (concise)

Laboratory Exercise — Student Registration Form
Case Study
Build a small web app that allows adding student registrations via a form. Features:




SpringMVC
names.
 Project: Maven, Java
 Dependencies:
 Spring Web
 Thymeleaf
 Spring Boot Starter Validation
 (Optional) Spring Data JPA, H2
1. Show a registration form (GET /students/new).
2. Submit the form (POST /students).
3. If validation errors, re-display the form with error messages.
4. On success, redirect to a confirmation page or list page (GET /students), showing a
success flash message.
5. (Optional) Store students in H2 and show all registrations.
:Controllerreceives HTTP requests, prepares model data, and returns view
Thymeleaf: Template engine that renders HTML on server; integrates tightly with Spring
MVC model attributes. Use th:object, th:field, th:errors, etc.
Form binding: Bind a form to a Java object (command object), letting Spring populate
fields automatically.
Validation: Use annotations like @NotBlank, @Email, @Size and check
BindingResult in controller to detect errors.
Redirect-After-Post: After successful POST, redirect to avoid duplicate form submission on
browser refresh. Use flash attributes to pass success messages.
If you persist, annotate with
Directory layout (recommended):
spr ing- thymeleaf-form/
and add on
├─ s rc/main/java/com/example/form/
│
│
│
│
│
├─ Spr ingFormApp.java
├─ cont roller /StudentCont roller .java
├─ model/Student .java
├─ repos i tory/StudentRepos i tory.java
└─ service/StudentService.java
└─ s rc/main/ resources /
├─ templates /
│
│
│
│
├─ students/
│
│
│
├─ form.html
├─ list .html
└─
└─ appsulicccaet isosn.h.ptmrolper t ies
(opt ional)
(opt ional)
package com.example.form.model;
impor t jakar ta.per s i s tence.*; // remove if not us ing JPA
impor t jakar ta.validat ion.cons t raint s .*;
public clas s Student {
pr ivate Long id; // opt ional if per s i s t ing
@NotBlank(mes sage = "Roll number i s requi red")
pr ivate St r ing rollNumber ;
@NotBlank(mes sage = "Fi r s t name i s requi red")
@Size(max = 50)
pr ivate St r ing fi r s tName;
pr ivate St r ing las tName;
@Email(mes sage = "Enter a valid email")
@NotBlank(mes sage = "Email i s requi red")
pr ivate St r ing email;
@NotBlank(mes sage = "Depar tment i s requi red")
pr ivate St r ing depar tment ;
@Min(value = 1, mes sage = "Year mus t be between 1 and 4")
@Max(value = 4, mes sage = "Year mus t be between 1 and 4")
pr ivate Integer year ;
// get ter s and set ter s
}
Sample Code (key parts)
1) Studentmodel(form-backing bean)
@Ent i ty @Id @GeneratedValuied.
2) Controller: StudentController
package com.example.form.cont roller ;
impor t com.example.form.model.Student ;
impor t jakar ta.validat ion.Valid;
impor t org. spr ingf ramework. s tereotype.Cont roller ;
impor t org. spr ingf ramework.ui.Model;
impor t org. spr ingf ramework.validat ion.BindingResul t ;
impor t org. spr ingf ramework.web.bind.annotat ion.*;
impor t org. spr ingf ramework.web. servlet .mvc. suppor t .Redi rectAt t r ibutes ;
impor t java.ut il.Li s t ;
impor t java.ut il.Ar rayLi s t ;
@Cont roller
@Reques tMapping("/ s tudent s ")
public clas s StudentCont roller {
//
pr ivate
in-memor lyist for demo;
Li s t<Student>
replace
s tudentStore
wi th service/ repo for persistence
final = new Ar rayLi s t<>();
// Show empty form
@GetMapping("/new")
public St r ing showForm(Model model) {
model.addAt t r ibute(" s tudent ",
return " s tudent s /form";
newStudent ());
}
// Proces s form
@Pos tMapping
public St r ing submi tForm(@Val@idModelAt t r ibute(" s tudent ") Student s tudent ,
BindingResul t
Redi rectAt t r ibutes
Model model) {
bindingResul t ,
redi rectAt t r ibutes ,
if (bindingResul t .hasEr ror s ()) {
// validat ion failed — showform again wi th er rors
return " s tudent s /form";
}
// success: store student (or call service.save(student ))
s tudentStore.add( s tudent );
// add flash at t r ibute to showsuccess af ter redi rect
redi rectAt t r ibutes .addFlashAt t r ibute(" succes sMes sage", "Student
regi s tered succes s fully!");
return " redi rect :/ s tudent s ";
}
// List students and showflash messageif present
@GetMapping
public St r ing li s tStudent s (Model model) {
model.addAt t r ibute(" s tudent s ",
return " s tudent s /li s t ";
s tudentStore);
}
// Opt ional: succes s page
@GetMapping("/ succes s ")
public St r ing succes s () {
return " s tudent s / succes s ";
}
}
3) Thymeleaf form template:
src/main/resources/templates/students/form.html
<!DOCTYPE html>
<html xmlns : th="ht tp://www. thymeleaf.org">
<head>
<meta char set="UTF-8"/>
<t i t le>Student Regi s t rat ion</ t i t le>
<link rel=" s tylesheet " th:href="@{/ s tyles .cs s }" />
</head>
<body>
<h1>Student Regi s t rat ion</h1>
<form th:act ion="@{/ s tudent s }" th:object="${ s tudent }" method="pos t ">
<div>
<label>Roll Number :</label>
<input th:field="*{ rollNumber }" />
<div th:if="${#fields .hasEr ror s (' rollNumber ')}"
th:er ror s="*{ rollNumber }"></div>
</div>
<div>
<label>Fi r s t name:</label>
<input th:field="*{fi r s tName}" />
<div th:if="${#fields .hasEr ror s ('fi r s tName')}"
th:er ror s="*{fi r s tName}"></div>
</div>
<div>
<label>Las t name:</label>
<input th:field="*{las tName}" />
</div>
<div>
<label>Email:</label>
<input th:field="*{email}" />
<div th:if="${#fields .hasEr ror s ('email')}" th:er ror s="*{email}"></div>
</div>
<div>
<label>Depar tment :</label>
<input th:field="*{depar tment }" />
<div th:if="${#fields .hasEr ror s ('depar tment ')}"
th:er ror s="*{depar tment }"></div>
</div>
<div>
<label>Year :</label>
<input type="number " th:field="*{year }" min="1" max="4" />
<div th:if="${#fields .hasEr ror s ('year ')}" th:er ror s="*{year }"></div>
</div>
<div>
<but ton type=" submi t ">Regi s ter</but ton>
</div>
</form>
<p><a th:href="@{/ s tudent s }">View all s tudent s</a></p>
</body>
</html>
5)
4) List template:
<!DOCTYPE html>
<html xmlns : th="ht tp://www. thymeleaf.org">
<head><meta char set="UTF-8"/><t i t le>Student s</ t i t le></head>
<body>
application.properties (basic)
templates/students/list.html
spr ing. thymeleaf.cache=fal se
spr ing.mvc.view.prefix=/ templates /
spr ing.mvc.view. suffix=.html
# if us ing H2 per s i s tence (opt ional)
spr ing.datasource.ur l=jdbc:h2:mem:formsdb;DB_CLOSE_DELAY=-1
spr ing.datasource.dr iverClas sName=org.h2.Dr iver
spr ing.jpa.hibernate.ddl-auto=update
spr ing.h2.console.enabled=t rue
<h1>Regi s tered Student s</h1>
<div th:if="${ succes sMes sage}">
<s t rong th: text="${ succes sMes sage}"></ s t rong>
</div>
<table border="1">
<thead>
<t r><th>Roll</ th><th>Name</ th><th>Email</ th><th>Dept</ th><th>Year</ th></ t r>
</ thead>
<tbody>
<t r th:each=" s : ${ s tudent s }">
<td th: text="${ s . rollNumber }">roll</ td>
<td th: text="${ s .fi r s tName + ' ' + s .las tName}">name</ td>
<td th: text="${ s .email}">email</ td>
<td th: text="${ s .depar tment }">dept</ td>
<td th: text="${ s .year }">year</ td>
</ t r>
</ tbody>
</ table>
<p><a th:href="@{/ s tudent s /new}">Regi s ter another s tudent</a></p>
</body>
</html>
Use @Valid on controller method and check
In template use th:errors to show field-specific messages.
You can show global errors with
.
Note: Spring Boot auto-configures Thymeleaf; you generally don't need
view.prefix/suffix.
and over
BindingResul t .
#fields.hasGlobalEr rors()
#fields.globalEr rors()
th:each
Validation & Error Display



Run & Test
Deliverables
Security / CSRF note

Exercises / Variations for students
Optional: Persisting with Spring Data JPA












1.Buildandrun:
For lab simplicity avoid adding Spring Security unless students are ready.
Projectsource code (zip or Git repo).
Screenshots of the form, validation errors, and list page with success message.
Short report explaining validation rules and any optional features implemented.
(If persisted) screenshot of H2 console showing saved records.
, create
, inject service into controller and call
.
list by fetching repo.findAll().
Add server-side duplicateroll number checkandshow error.
Add file upload (profile photo) using MultipartFile.
Add client-side validation (HTML5 / JavaScript) in addition to server validation.
Add edit/delete student functionality (forms for edit and confirm delete).
Add styling (Bootstrap) and Thymeleaf layout fragments (header.html,
footer .html).
SpringBoot’s security(if present) enables CSRF by default. If you add Spring Security for
experiments, ensure forms include CSRF token:
or run main class from IDE.
2. Open browser: http://localhost:8080/students/new — fill and submit form.
3. On success you’ll be redirected to /students showing list and success message.
4. Try submitting invalid data (blank required fields) and verify error messages.
Add@Ent i ty to Student StudentReposi tory extends
JpaReposi tory<Student , Long>
repo.save(student )
Provide /students
<input type="hidden"
th:name="${_csr f.parameterName}" th:value="${_csr f. token}"/>
mvn spr ing-boot : run
Output:-
deviprasad shet ty
admin@ecogauge.com
4
2
CS
deviprasad shet ty
devi photo. jpg
