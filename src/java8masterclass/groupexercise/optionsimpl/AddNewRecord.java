package java8masterclass.groupexercise.optionsimpl;

import java8masterclass.groupexercise.CommandAction;
import java8masterclass.groupexercise.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddNewRecord implements CommandAction {

  private static final String ENTER_EMPLOYEE = "Enter %s: ";

  @Override
  public void doAction(List<Employee> employees) {
    Integer employeeNumber = getValidEmployeeNumber(employees);

    System.out.printf(ENTER_EMPLOYEE, "First Name");
    String employeeFirstName = input.next();
    System.out.printf(ENTER_EMPLOYEE, "Middle Name");
    String employeeMiddleName = input.next();
    System.out.printf(ENTER_EMPLOYEE, "Last Name");
    String employeeLastName = input.next();

    LocalDate employeeHiringDate = getHiringDate();
    createAndPrintEmployee(
        employees,
        employeeNumber,
        employeeHiringDate,
        employeeFirstName,
        employeeMiddleName,
        employeeLastName);
  }

  private void createAndPrintEmployee(
      List<Employee> employees,
      Integer employeeNumber,
      LocalDate employeeHiringDate,
      String employeeFirstName,
      String employeeMiddleName,
      String employeeLastName) {

    Employee newEmployee =
        new Employee(
            employeeNumber,
            employeeFirstName,
            employeeMiddleName,
            employeeLastName,
            employeeHiringDate);
    employees.add(newEmployee);

    printEmployeeInfo(newEmployee);
  }

  private Integer getValidEmployeeNumber(List<Employee> employees) {
    Integer employeeNumber = null;
    String strEmpNumber;
    boolean validEmpNumber;

    do {
      System.out.print("\nEnter Employee Number: ");
      strEmpNumber = input.next();
      validEmpNumber = isValidEmployeeNumber(strEmpNumber);

      if (validEmpNumber) {
        employeeNumber = Integer.valueOf(strEmpNumber);
        if (employees.stream().map(Employee::getEmployeeNumber).anyMatch(employeeNumber::equals)) {
          System.out.println("Invalid entry. Employee number already exist. Try again.");
          validEmpNumber = false;
        }
      }
    } while (!validEmpNumber);

    return employeeNumber;
  }

  private LocalDate getHiringDate() {
    String employeeHiringDate;
    boolean validHiringDate;
    LocalDate hiredDate = null;
    do {
      System.out.printf(ENTER_EMPLOYEE, "Date Hired (yyyy-MM-dd)");
      employeeHiringDate = input.next();
      validHiringDate = isValidDate(employeeHiringDate);

      if (!validHiringDate) {
        System.out.println("Invalid entry. Try again.");
      } else {
        hiredDate = parseStringDateToLocalDate(employeeHiringDate);
        if (isFutureDate(hiredDate)) {
          validHiringDate = false;
          System.out.println("Invalid date. Future date is not allowed.");
        }
      }
    } while (!validHiringDate);
    return hiredDate;
  }

  public void printEmployeeInfo(Employee newEmployee) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a");
    System.out.println(
        "\n\nEmployee record added successfully: "
            + LocalDateTime.now().format(dateTimeFormatter));
    System.out.println("Number: " + newEmployee.getEmployeeNumber());
    System.out.println("Name: " + newEmployee.getFullName());
    System.out.println(
        "Date Hired: " + formatDateMMMddyyyy(newEmployee.getHiringDate().toString()) + "\n");
  }
}
