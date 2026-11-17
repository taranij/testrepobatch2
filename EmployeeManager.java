import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A simple console application for managing employee records.
 * Compile with: javac EmployeeManager.java
 * Run with:     java EmployeeManager
 */
public class EmployeeManager {
    private static final Map<Integer, Employee> employees = new LinkedHashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Employee Management ===");
            System.out.println("1. Add employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Search employee by ID");
            System.out.println("4. Exit");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> displayEmployees();
                case 3 -> searchEmployee();
                case 4 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option. Please choose 1–4.");
            }
        }
        scanner.close();
    }

    private static void addEmployee() {
        int id = readInt("Employee ID: ");
        if (employees.containsKey(id)) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readText("Name: ");
        String department = readText("Department: ");
        double salary = readNonNegativeDouble("Salary: ");
        employees.put(id, new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }

        System.out.printf("%-12s %-24s %-20s %12s%n", "Employee ID", "Name", "Department", "Salary");
        System.out.println("--------------------------------------------------------------------------");
        for (Employee employee : employees.values()) {
            System.out.printf("%-12d %-24s %-20s %12.2f%n",
                    employee.id, employee.name, employee.department, employee.salary);
        }
    }

    private static void searchEmployee() {
        int id = readInt("Enter employee ID to search: ");
        Employee employee = employees.get(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.println("Employee found:");
            System.out.println("ID: " + employee.id);
            System.out.println("Name: " + employee.name);
            System.out.println("Department: " + employee.department);
            System.out.printf("Salary: %.2f%n", employee.salary);
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (Double.isFinite(value) && value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Show the common validation message below.
            }
            System.out.println("Please enter a valid non-negative salary.");
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static final class Employee {
        private final int id;
        private final String name;
        private final String department;
        private final double salary;

        private Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
    }
}
