package java8masterclass.groupexercise.optionsimpl;

import java8masterclass.groupexercise.CommandAction;
import java8masterclass.groupexercise.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteEmployeeRecord implements CommandAction {
  @Override
  public void doAction(List<Employee> employees) {

    if (employees == null || employees.isEmpty()) {
      System.out.println("No employee records to delete.\n");
      return;
    }

    String strEmpNumber;
    boolean validEmpNumber;
    do {
      System.out.print("\nEnter Employee Number: ");
      strEmpNumber = input.next();
      validEmpNumber = isValidEmployeeNumber(strEmpNumber);
    } while (!validEmpNumber);

    Integer finalEmployeeNumber = Integer.valueOf(strEmpNumber);
    Long countToDelete =
        employees.stream()
            .filter(employee -> employee.getEmployeeNumber() == finalEmployeeNumber)
            .collect(Collectors.toList())
            .stream()
            .count();

    if (countToDelete == 1) {
      employees.removeIf(employee -> employee.getEmployeeNumber() == finalEmployeeNumber);
      System.out.println(finalEmployeeNumber + " Employee Record has been deleted.\n");
      printEmployees(employees);
    } else {
      System.out.println("Couldn't delete record. Record not found.\n");
    }
  }
}
