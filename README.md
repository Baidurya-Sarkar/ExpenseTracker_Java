# ExpenseTracker_Java

In an era of Massive expenditure and inflation,it is very essential to keep track of expenses and savings to save for later.**ExpenseTracker** is a lightweight Java Swing application that helps you manage your personal expenses effectively. Built using core Java concepts like Arrays, HashMap, and File I/O, this tool provides a clean and functional GUI to track, view, and export your daily expenditures.

---

## Features

-  **Add Expenses**: Enter date, category, amount, and description
-  **View & Delete**: Browse all expenses in a scrollable table, delete with one click
-  **Monthly Stats**: Real-time monthly total expenses displayed
-  **Export to File**: Save all logged expenses to `expenses_export.txt`
-  **User-Friendly GUI**: Clean layout with intuitive buttons and color accents

---

## Project Structure

ExpenseTracker
|── ExpenseTracker.java # Main GUI application
|── Expense.java # Expense data model
|__ expenses_export.txt # Auto-generated file after export

---

## How to Run:

### Prerequisites
- Java JDK 8 or above

### Compile and Run
javac ExpenseTracker.java
java ExpenseTracker

