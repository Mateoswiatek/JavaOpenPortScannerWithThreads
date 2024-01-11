# Application Description

## Code Overview
//TODO zrobić poprawioną wersję README

### Main.java File

The `Main.java` file contains the main class `Main`, which executes the core logic of the program. The application checks the availability of ports within a specified range of parameters and writes the results to files.

### CheckThread.java File

The `CheckThread.java` file contains the `CheckThread` class, which represents a thread responsible for checking port availability.

## Running Instructions

To run the application, follow these steps:

1. Compile the source code using the Java compiler.

2. Run the application, providing appropriate arguments. For example:

Where `127.0.0.1` is the IP address you want to check.

## Other Notes

- Ensure that the application has necessary permissions to perform operations on ports.
- It's possible to modify the application parameters by passing different arguments when running the program.
## Possibilities for Development

The project serves as a solid foundation for further expansion and improvement. Below are areas to consider for development to enhance functionality, improve performance, and flexibility of the application:

### 1. Additional Network Protocols and Features

Consider adding support for different network protocols to check availability, such as UDP, HTTP, ICMP, allowing for more comprehensive network testing.

### 2. Graphical User Interface (GUI)

Create a graphical interface for the application that enables users to input IP addresses, ports, and control running tests and displaying results in a user-friendly manner.

### 3. Configuration Management

Implement a configuration management system that allows users to modify application parameters (e.g., IP addresses, file names) without altering the source code.

### 4. Results Handling in a Database

Add the capability to save test results to a database, enabling analysis of historical data and generating reports.
