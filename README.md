# hotel_management_
Developed a console-based Hotel Management System using Java and MySQL, applying object-oriented principles such as encapsulation, inheritance, and polymorphism.
A Java-based console application that manages hotel operations such as customer booking, updation and deletion . Built with Object-Oriented Programming principles and MySQL database integration using JDBC.

## 🔧 Tech Stack
- Java (OOP Concepts)
- MySQL
- JDBC (Java Database Connectivity)
- VS Code

## ✨ Features
- Add/View/Update/Delete customer details
- Show current date and time 
- Store data persistently using MySQL

## 🧱 OOP Concepts Used
- **Encapsulation**: Used private fields with getters/setters for all domain models.
- **Inheritance**: Common features inherited by specialized classes (e.g., `Person` → `Customer`, `Staff`).
- **Polymorphism**: Overloaded methods for data entry and search.
- **Abstraction**: Separated data access logic using DAO-style classes.

## 💾 Database
- Tables: `registration_id`, `guest_name`, `contact_number`
- Connected via JDBC using prepared statements for security and efficiency.

## 🚀 Getting Started
1. Clone the repository
2. Set up MySQL database using the provided schema
3. Compile and run the Java application
