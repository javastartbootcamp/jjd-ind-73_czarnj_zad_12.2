import java.io.*;

public class EmployeeManager {
    private static final String SEMICOLON_MARK = ";";
    private static final String FILE_PROBLEM_MSG = "There was a problem with reading file %s%n";

    public static Employee[] readEmployeesFromFile(File file, int maxEmployeesAmount) {
        Employee[] employees = new Employee[maxEmployeesAmount];
        try (var br = new BufferedReader(new FileReader(file))) {
            String nextLine;
            int counter = 0;
            while ((nextLine = br.readLine()) != null && counter < maxEmployeesAmount) {
                Employee employee = extractEmployeeFromLine(nextLine);
                employees[counter++] = employee;
            }
            return employees;
        } catch (IOException ex) {
            System.err.printf(FILE_PROBLEM_MSG, file.getName());
            return new Employee[0];
        }
    }

    public static double getAverageSalary(Employee[] employees) {
        double sum = 0;
        int employeesAmount = 0;
        for (int i = 0; i < employees.length && employees[i] != null; i++) {
            sum += employees[i].getSalary();
            employeesAmount++;
        }
        return sum / employeesAmount;
    }

    public static double getSmallestSalary(Employee[] employees) {
        double smallestSalary = employees[0].getSalary();
        for (int i = 0; i < employees.length && employees[i] != null; i++) {
            if (employees[i].getSalary() < smallestSalary)  {
                smallestSalary = employees[i].getSalary();
            }
        }
        return smallestSalary;
    }

    public static double getHighestSalary(Employee[] employees) {
        double highestSalary = employees[0].getSalary();
        for (int i = 0; i < employees.length && employees[i] != null; i++) {
            if (employees[i].getSalary() > highestSalary)  {
                highestSalary = employees[i].getSalary();
            }
        }
        return highestSalary;
    }

    public static int getEmployeesAmountInDepartment(String department, Employee[] employees) {
        int counter = 0;
        for (int i = 0; i < employees.length && employees[i] != null; i++) {
            if (employees[i].getDepartment().equals(department))  {
                counter++;
            }
        }
        return counter;
    }

    public static void saveStats(Employee[] employees, File file) {
        String stats = buildStats(employees);
        try (var br = new BufferedWriter(new FileWriter(file))) {
            br.write(stats);
        } catch (IOException ex) {
            System.err.printf(FILE_PROBLEM_MSG, file.getName());
        }
    }

    private static String buildStats(Employee[] employees) {
        double avgSalary = getAverageSalary(employees);
        double minSalary = getSmallestSalary(employees);
        double maxSalary = getHighestSalary(employees);
        int itDepartmentEmployees = getEmployeesAmountInDepartment(Employee.IT_DEPARTMENT, employees);
        int supportDepartmentEmployees = getEmployeesAmountInDepartment(Employee.SUPPORT_DEPARTMENT,
                employees);
        int managementDepartmentEmployees = getEmployeesAmountInDepartment(Employee.MANAGEMENT_DEPARTMENT,
                employees);
        return String.format("Średnia wypłata: %.2f%n", avgSalary) +
                String.format("Minimalna wypłata: %.2f%n", minSalary) +
                String.format("Maksymalna wypłata: %.2f%n", maxSalary) +
                String.format("Liczba pracowników %s: %d%n", Employee.IT_DEPARTMENT,
                        itDepartmentEmployees) +
                String.format("Liczba pracowników %s: %d%n", Employee.SUPPORT_DEPARTMENT,
                        supportDepartmentEmployees) +
                String.format("Liczba pracowników %s: %d%n", Employee.MANAGEMENT_DEPARTMENT,
                        managementDepartmentEmployees);
    }

    private static Employee extractEmployeeFromLine(String line) {
        String[] splittedLine = line.split(SEMICOLON_MARK);
        String firstName = splittedLine[0];
        String lastName = splittedLine[1];
        String pesel = splittedLine[2];
        String department = splittedLine[3];
        double salary = Double.parseDouble(splittedLine[4]);
        return new Employee(firstName, lastName, pesel, department, salary);
    }
}
