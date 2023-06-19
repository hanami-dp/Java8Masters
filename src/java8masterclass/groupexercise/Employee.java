package java8masterclass.groupexercise;

import java.time.LocalDateTime;

public class Employee {

    public Employee(int employeeNumber, String firstName, String middleName, String lastName, String hiringDate) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.hiringDate = hiringDate;
    }

    private int employeeNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private String fullName;

    private String hiringDate;

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return getNonNullString(firstName).concat(" " + getNonNullString(middleName)).concat(" " + getNonNullString(lastName));
    }

    public String getHiringDate() {
        return hiringDate;
    }

    private String getNonNullString(String value) {
        return value == null ? "" : value;
    }

    public void printInfo() {
        System.out.println("Employee record added successfully: " + LocalDateTime.now());
        System.out.println("Number: " + employeeNumber);
        System.out.println("Name: " + getFullName());
        System.out.println("Date Hired: " + hiringDate + "\n");
    }
}
