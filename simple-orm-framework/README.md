# Simple ORM Framework (Java)

![Java](https://img.shields.io/badge/Java-17-orange)
![JDBC](https://img.shields.io/badge/JDBC-Database-blue)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Status](https://img.shields.io/badge/Project-Learning%20ORM-green)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A lightweight **Object Relational Mapping (ORM) framework built from scratch using Java and JDBC**.

This project was created to **understand how modern ORMs like Hibernate work internally** by implementing core ORM concepts manually using **reflection, annotations, and dynamic SQL generation**.

---

# Project Overview

This project implements a **mini ORM engine** with the following features:

- Annotation based entity mapping
- Reflection based metadata extraction
- Dynamic SQL generation
- CRUD operations
- Transaction management
- JDBC integration
- Swing GUI demonstration

The GUI application interacts **only with the ORM layer**, not directly with JDBC.

This mimics how real backend frameworks separate **application logic from database interaction**.

---

# Application Demo (Swing GUI)

The project includes a **Java Swing GUI** to demonstrate how the ORM works in a real application.

Available operations:

- Insert Student
- Find Student
- Update Student
- Delete Student
- View All Students

The GUI interacts with the **ORM session layer**, which then communicates with the database.

This demonstrates a **layered architecture similar to enterprise frameworks**.

---

# Architecture Diagram

```
Application Layer (Swing GUI)
           │
           ▼
     ORM Session Layer
     (SimpleOrmSession)
           │
           ▼
 Metadata Extraction Layer
   (OrmMetadataExtractor)
           │
           ▼
  Annotation Mapping
 (@Entity @Table @Column @Id)
           │
           ▼
   Dynamic SQL Builder
           │
           ▼
         JDBC
           │
           ▼
       MySQL Database
```

---

# Project Structure (Maven)

```
simple-orm-framework
│
├── src
│   │
│   ├── main
│   │   ├── java
│   │   │
│   │   └── com.yourcompany.simpleorm
│   │        │
│   │        ├── annotation
│   │        │   ├── Entity
│   │        │   ├── Table
│   │        │   ├── Column
│   │        │   └── Id
│   │        │
│   │        ├── jdbc
│   │        │   └── ConnectionManager
│   │        │
│   │        ├── metadata
│   │        │   └── OrmMetadata
│   │        │
│   │        ├── util
│   │        │   └── OrmMetadataExtractor
│   │        │
│   │        ├── session
│   │        │   ├── OrmSession
│   │        │   └── SimpleOrmSession
│   │        │
│   │        ├── exception
│   │        │
│   │        └── simple_orm_framework
│   │             ├── Student
│   │             ├── StudentDriver (Swing GUI)
│   │             └── App
│
└── pom.xml
```

---

# How To Run The GUI

Since this is a **Maven project**, the main GUI file is located here:

```
src/main/java/com/yourcompany/simpleorm/simple_orm_framework/StudentDriver.java
```

Run this file to launch the **Student Manager GUI application**.

---

# Database Setup (Important)

Currently the ORM **does not automatically create the database or tables**.

You must create them manually before running the application.

Example MySQL setup:

```sql
CREATE DATABASE simple_orm_db;

USE simple_orm_db;

CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roll_number INT UNIQUE,
    name VARCHAR(100),
    age INT,
    course VARCHAR(100)
);
```

Future versions may support **automatic schema generation similar to Hibernate**.

---

# Example Entity Mapping

```java
@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "roll_number")
    private Integer rollNumber;

    @Column(name = "name")
    private String name;

}
```

---

# ORM Usage Example

```java
Connection connection = ConnectionManager.getConnection();

SimpleOrmSession session = new SimpleOrmSession(connection);

Student student = new Student();

student.setRollNumber(101);
student.setName("Alice");
student.setAge(21);
student.setCourse("Computer Science");

session.save(student);

Optional<Student> result =
    session.findById(Student.class, student.getId());
```

---

# Comparison With Hibernate

| Feature | This ORM | Hibernate |
|------|------|------|
Entity Mapping | Custom annotations | JPA annotations |
Reflection Mapping | Yes | Yes |
Dynamic SQL Generation | Yes | Yes |
Session Management | Basic | Advanced |
Transaction Handling | Manual | Automatic |
Caching | No | Yes |
Lazy Loading | No | Yes |
Relationship Mapping | No | Yes |
Schema Generation | No | Yes |
Query Language | SQL | HQL / JPQL |

This project focuses on **core ORM fundamentals**, while Hibernate provides a full enterprise-level ORM solution.

---

# Technologies Used

- Java
- JDBC
- Maven
- MySQL
- Swing
- Java Reflection API

---

# What I Learned From This Project

Developing this ORM helped me understand the **internal architecture of frameworks like Hibernate and JPA**.

Key concepts explored:

### Reflection and Annotation Processing
- Extracting metadata from entity classes
- Mapping class fields to database columns

### Dynamic SQL Generation
- Building SQL queries using entity metadata

### Object Relational Mapping
- Converting database records into Java objects
- Mapping SQL types to Java types

### ORM Architecture
- Session pattern
- Metadata abstraction
- Layered framework design

### JDBC Integration
- PreparedStatement usage
- ResultSet mapping
- Transaction control

---

# Future Roadmap

Several advanced features could extend this ORM.

### Relationship Mapping

Support entity relationships such as:

```
@OneToMany
@ManyToOne
@OneToOne
```

Example:

```
Student → Department
Student → Courses
```

This would require implementing **foreign key detection and relationship mapping**.

---

### Join Query Support

Future usage example:

```java
session.findWithJoin(Student.class, Department.class);
```

Possible implementation approach:

- detect foreign key metadata
- dynamically generate JOIN SQL
- map nested objects

---

### Schema Generation

Currently the database must be created manually.

Future versions could automatically create tables using entity metadata similar to:

```
hibernate.hbm2ddl.auto = update
```

Implementation idea:

- scan entity metadata
- generate CREATE TABLE statements
- execute during startup

---

### Generic Query Builder

Support dynamic queries such as:

```java
session.findByColumn(Student.class, "roll_number", 101);
```

or

```java
session.findByColumn(Student.class, "name", "Alice");
```

---

### First Level Cache

Add a session-level cache to reduce repeated database queries.

---

# Why This Project Matters

Most developers **use ORMs without understanding how they work internally**.

Building one from scratch provides insight into:

- how objects are translated into SQL
- how metadata drives query generation
- how frameworks abstract database access

This knowledge helps developers **better understand and debug Hibernate-based systems**.

---

# Author

Built as a **learning project to explore ORM framework design and backend architecture using Java**.