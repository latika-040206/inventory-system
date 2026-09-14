Inventory & Order Processing System (CLI)
A pure command-line Java application designed for catalog management, stock replenishment, dynamic discount evaluation, and transactional CSV persistence.

1. System Requirements & Environment Setup
Prerequisites
Java Development Kit (JDK): Version 11, 17, or later.

Operating System: Windows, macOS, or Linux.

Terminal: Command Prompt (cmd), PowerShell, or Bash.

Environment Verification
To verify your system has the Java compiler and runtime configured properly, run:

javac -version

java -version

If neither command is recognized, ensure your JDK bin directory is added to your system's PATH environment variable.

2. Dependencies & Installation
External Libraries: None.

The project relies exclusively on the standard Java Class Library (java.base, specifically java.io, java.util).

No external build tools (Maven/Gradle) or third-party packages are required.

3. Configuration & Data Files
The system uses a CSV file as its primary persistent database:

File Location: data/inventory.csv

Schema: TYPE,ID,NAME,PRICE,QUANTITY,EXTRA

TYPE: Product category (ELECTRONICS or PERISHABLE)

ID: Unique product identifier (e.g., E101, P201)

NAME: Display name of the item

PRICE: Unit price (double)

QUANTITY: Stock on hand (integer)

EXTRA: Warranty duration in months for electronics, or expiration date (YYYY-MM-DD) for perishable goods

Note: The data/inventory.csv file is read at startup and automatically updated on disk whenever an order is completed or stock is replenished.

4. Compilation & Execution Instructions
Step 1: Open Terminal in Root Directory
Navigate to the root directory:
cd inventory-system

Step 2: Compile All Classes
Compile all modules and source files into the out target directory:

javac -d out -sourcepath src src\com\store\Main.java

Step 3: Run the Application
Execute the compiled bytecode via standard CLI:

java -cp out com.store.Main

5. Architecture & Object-Oriented Principles
Inheritance & Polymorphism:

com.store.model.Item: Abstract base class enforcing core item properties (id, name, unitPrice, stock).

com.store.model.ElectronicsItem & com.store.model.PerishableItem: Concrete implementations overriding serialization (toCsvRecord()) and pricing rules.

com.store.model.Discountable: Common interface applied to items eligible for dynamic order discounts.

Persistence Layer:

com.store.service.StoreService: Manages file I/O operations (BufferedReader/BufferedWriter) to ensure state preservation on local disk.

Defensive Error Handling:

Custom exceptions (InsufficientStockException, ItemNotFoundException) manage application boundary faults without terminating the CLI loop.

6. Sample Usage Walkthrough
List All Products: Enter 1 at the main menu to view active inventory, prices, and product-specific attributes.

Purchase an Item: Enter 2, input product ID E101, and enter quantity 4. The system validates available stock, calculates a bulk discount, prints an itemized receipt, and decrements stock in data/inventory.csv.

Restock: Enter 3, input product ID P201, and enter 20 to restock.

Exit: Enter 4 to terminate the session safely.