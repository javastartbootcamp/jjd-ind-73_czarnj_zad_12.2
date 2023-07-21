public class Employee {
    public static final String IT_DEPARTMENT = "IT";
    public static final String SUPPORT_DEPARTMENT = "Support";
    public static final String MANAGEMENT_DEPARTMENT = "Management";
    private String firstName;
    private String lastName;
    private String pesel;
    private String department;
    private double salary;

    public Employee(String firstName, String lastName, String pesel, String department, double salary) {
        setDepartment(department);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (!department.equals(IT_DEPARTMENT) && !department.equals(SUPPORT_DEPARTMENT) &&
                !department.equals(MANAGEMENT_DEPARTMENT)) {
            throw new UnknownDepartmentException(String.format("%s department doesn't exist", department));
        }
        this.department = department;
    }
}
