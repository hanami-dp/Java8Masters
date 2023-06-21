package java8masterclass.groupexercise.optionsimpl;

import java8masterclass.groupexercise.CommandAction;
import java8masterclass.groupexercise.Employee;
import java8masterclass.groupexercise.Option;
import java8masterclass.groupexercise.TypeAndRepeatingAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchEmployeeRecord implements CommandAction {

  private Option[] searchActions;

  @Override
  public void doAction(List<Employee> employees) throws NoSuchFieldException {

    printSearchAction();
    int searchSelection;
    do {
      System.out.print("\nSelect action: ");
      searchSelection = input.nextInt();
      if (searchSelection == -1) {
        System.out.println();
      } else {
        List<Employee> result = search(employees, searchSelection);
        printEmployees(result);
        return;
      }
    } while (searchSelection != -1);
  }

  private List<Employee> search(List<Employee> employees, int searchSelection) {
    List<Employee> searchResult = new ArrayList<>();
    Predicate<Employee> employeePredicate = null;
    switch (searchSelection) {
      case 1:
        String strEmpNumber;
        boolean validEmpNumber;

        do {
          System.out.print("\nEnter Number: ");
          strEmpNumber = input.next();
          validEmpNumber = isValidEmployeeNumber(strEmpNumber);
        } while (!validEmpNumber);
        Integer finalEmployeeNumber = Integer.valueOf(strEmpNumber);
        employeePredicate = e -> e.getEmployeeNumber() == finalEmployeeNumber;
        break;
      case 2:
        System.out.print("\nEnter First Name: ");
        final String employeeFirstName = input.next();
        employeePredicate = e -> e.getFirstName().equalsIgnoreCase(employeeFirstName);
        break;
      case 3:
        System.out.print("\nEnter Middle Name: ");
        final String employeeMiddleName = input.next();
        employeePredicate = e -> e.getMiddleName().equalsIgnoreCase(employeeMiddleName);
        break;
      case 4:
        System.out.print("\nEnter Last Name: ");
        final String employeeLastName = input.next();
        employeePredicate = e -> e.getLastName().equalsIgnoreCase(employeeLastName);
        break;
      case 5:
        String dateHired;
        boolean validHiringDate;
        do {
          System.out.print("\nEnter Date Hired: ");
          dateHired = input.next();
          validHiringDate = isValidDate(dateHired);
          if (!validHiringDate) {
            System.out.println("Invalid entry. Try again.");
          }
        } while (!validHiringDate);
        String finalDateHired = dateHired;
        // employeePredicate = e -> e.getHiringDate().equalsIgnoreCase(finalDateHired);
        break;
      default:
        System.out.println("Invalid entry.");
    }

    if (employeePredicate != null) {
      searchResult = employees.stream().filter(employeePredicate).collect(Collectors.toList());
    }

    return searchResult;
  }

  private void printSearchAction() throws NoSuchFieldException {
    System.out.println("\nChoose an action");
    TypeAndRepeatingAnnotations typeAndRepeatingAnnotations = new TypeAndRepeatingAnnotations();
    Class<?> c = typeAndRepeatingAnnotations.getClass();
    Field fd = c.getDeclaredField("searchActions");
    searchActions = fd.getAnnotationsByType(Option.class);
    Arrays.stream(searchActions).forEach(searchAction -> System.out.println(searchAction.name()));
  }
}
