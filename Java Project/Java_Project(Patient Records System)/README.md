# Patient Record System

A robust Java desktop application for managing patient records. It features a modern Java Swing UI, integrates with a MySQL database for reliable data storage, and uses Java NIO for efficient file handling (such as uploading and organizing patient reports).

## Features

- **Add Patient Records:** Store patient details including name, age, and disease.
- **File Management:** Upload patient reports (like PDFs or images), which are securely copied and stored in the local `reports/` directory.
- **View Patients:** A beautiful, responsive Swing UI table that lists all patient records.
- **Delete Patients:** Easily remove patient records from both the database and the system.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK):** Version 8 or higher.
- **MySQL Server:** For the database backend.
- **MySQL JDBC Driver (`mysql-connector-java`):** Required to connect Java to the MySQL database. Add the `.jar` to your project's classpath.

## Database Setup

1. Open your MySQL client (like MySQL Workbench or CLI).
2. Execute the following SQL commands to create the database and the required table:

```sql
CREATE DATABASE patient_db;

USE patient_db;

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    disease VARCHAR(150),
    file_path VARCHAR(255)
);
```

3. Ensure your MySQL server is running on `localhost:3306`.
4. The application expects the default database credentials:
   - **Username:** `root`
   - **Password:** `password`
   
   *(If your MySQL credentials are different, please update lines 8 and 9 in `DBConnection.java`)*.

## Installation & Running

1. **Clone or Download** this project to your local machine.
2. Ensure you have a folder named `reports` within the `Project` directory (where the `.java` files reside), or let the application create it automatically upon the first file upload.
3. Compile all the Java files:
   ```bash
   javac -cp ".;path/to/mysql-connector-java.jar" *.java
   ```
4. Run the main application:
   ```bash
   java -cp ".;path/to/mysql-connector-java.jar" Main
   ```
   *(Note: Replace `path/to/mysql-connector-java.jar` with the actual path where your JDBC driver is located. On Linux/macOS, use `:` instead of `;` as the classpath separator).*

## Project Structure

- `Main.java` - The entry point of the application.
- `PatientUI.java` - Contains the Java Swing/AWT Graphical User Interface implementation.
- `DBConnection.java` - Manages the MySQL JDBC connection.
- `PatientDAO.java` - Data Access Object for handling CRUD operations on the database.
- `Patient.java` - The Patient model class.
- `FileHandler.java` - Handles Java NIO operations to read, save, and manage report files locally.

## Troubleshooting

- **`java.sql.SQLException: No suitable driver found for jdbc:mysql://...`**
  Make sure you have included the MySQL JDBC driver in your classpath when compiling and running the code.
- **`com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure`**
  Ensure your MySQL server is running and accessible on port `3306`.
- **"Unresolved compilation problem"**
  Ensure all `.java` files are compiled within the same package directory or classpath.
