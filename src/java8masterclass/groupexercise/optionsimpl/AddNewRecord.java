package java8masterclass.groupexercise.optionsimpl;

import java8masterclass.groupexercise.CommandAction;
import java8masterclass.groupexercise.Employee;

import java.util.List;

public class AddNewRecord implements CommandAction {

    private static final String ENTER_EMPLOYEE = "Enter Employee %s: ";

    @Override
    public void doAction(List<Employee> employees) {
        Integer employeeNumber = null;
        String employeeHiringDate;
        String strEmpNumber;
        boolean validEmpNumber;

        do {
            System.out.print("\nEnter Number: ");
            strEmpNumber = input.next();
            validEmpNumber = isValidEmployeeNumber(strEmpNumber);

            if (validEmpNumber) {
                employeeNumber = Integer.valueOf(strEmpNumber);
                if (employees.stream().map(Employee::getEmployeeNumber).anyMatch(employeeNumber::equals)){
                    System.out.println("Invalid entry. Employee number already exist. Try again.");
                    validEmpNumber = false;
                }
            }
        } while (!validEmpNumber);

        System.out.printf(ENTER_EMPLOYEE, "First Name");
        String employeeFirstName = input.next();
        System.out.printf(ENTER_EMPLOYEE, "Middle Name");
        String employeeMiddleName = input.next();
        System.out.printf(ENTER_EMPLOYEE, "Last Name");
        String employeeLastName = input.next();

        boolean validHiringDate;
        do {
            System.out.printf(ENTER_EMPLOYEE, "Hiring Date"); // todo: add format
            employeeHiringDate = input.next();
            validHiringDate = isValidDate(employeeHiringDate);
            if (!validHiringDate) {
                System.out.println("Invalid entry. Try again.");
            }
        } while (!validHiringDate);

        Employee newEmployee = new Employee(employeeNumber, employeeFirstName, employeeMiddleName, employeeLastName, null); // TODO: update handling of date
        employees.add(newEmployee);
        newEmployee.printInfo();
    }
}
