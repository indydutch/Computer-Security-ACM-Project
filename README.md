# <p align="center">**<u>Computer Security ACM Project</u>**</p>

### **Description:** Computer Security Access Control Matrix Project Code for Senior Design at Florida Polytechnic University. 

#### **Code Organization:** 

- Main.java: driver for the program. Implements a basic TUI/GUI for users to navigate.

- TableCreation.java: main object creation and handling class. Handles all of the database queries and major functions of the system.

- ShowTable.java: side class for showing the table (no column headers provided).

- DeleteTable.java: side class for deleting the table from the database.
  

#### **Code Building:** 

- IDE: Intellij ver. 2021.2.1.
- Programming Language: Java 16, corretto-16 (Amazon Corretto version 16.0.2).
- build.gradle: please ensure that your build.gradle file has all the necessary dependencies that are required for the database. Running on Gradle 7.1.
- SQL Database Type: SQLite ver. 3, SQLite JDBC ver. 3.36.0.3.

Before running the code, add the bot token ""  to the "Program Requirements" text box in the "Edit Configurations" for the "Main" program file. **<u>*CRITICAL*</u>**



#### **Team Contributions:**

Note: all team members worked and contributed as equally as possible with coding and researching for what we needed for this project. For formality's sake, here is a list of team members and what we are each contributing:

- Matthew Dutchess: TableCreation.java (all), DeleteTable.java (all), ShowTable.java (all), Main.java (functionality). 

- Calvin Hariprasad: Main.java (general structure, TUI/GUI).

  

#### **Imported Libraries:** 

- Java Utility Scanner (java.util.scanner): used for getting user input.
- Java SQL (java.sql.?): used for SQL querying.
- Java Utility Object (java.util.Objects): used for transforming objects and object variables to Strings, Integers, Boolean, etc.
