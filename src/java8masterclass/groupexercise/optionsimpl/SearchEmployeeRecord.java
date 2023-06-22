package java8masterclass.groupexercise.optionsimpl;

import java8masterclass.groupexercise.CommandAction;
import java8masterclass.groupexercise.Employee;
import java8masterclass.groupexercise.Option;
import java8masterclass.groupexercise.TypeAndRepeatingAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchEmployeeRecord implements CommandAction {

  @Override
  public void doAction(List<Employee> employees) throws NoSuchFieldException {

    printSearchAction();
    int searchSelection;
    boolean validSelection;
    do {
      System.out.print("\nSelect action: ");
      searchSelection = input.nextInt();
      validSelection = isValidSelection(searchSelection);
      if (validSelection) {
        if (searchSelection == -1) {
          System.out.println();
          return;
        } else {
          List<Employee> result = search(employees, searchSelection);
          printEmployees(result);
        }
      } else {
        System.out.println("Invalid entry. Try again.");
      }
    } while (!validSelection);
  }

  private boolean isValidSelection(int searchSelection) {
    return searchSelection == -1
        || searchSelection == 1
        || searchSelection == 2
        || searchSelection == 3;
  }

  private List<Employee> search(List<Employee> employees, int searchSelection) {
    List<Employee> searchResult = new ArrayList<>();
    Predicate<Employee> employeePredicate = null;

    switch (searchSelection) {
      case 1:
        Integer finalEmployeeNumber = Integer.valueOf(getValidEmployeeNumber());
        employeePredicate = e -> e.getEmployeeNumber() == finalEmployeeNumber;
        break;

      case 2:
        System.out.print("Enter Name: ");
        final String employeeName = input.next();
        employeePredicate = e -> e.getFullName().toUpperCase().contains(employeeName.toUpperCase());
        break;

      case 3:
        LocalDate finalParseHiredDate = getValidHireDate();
        employeePredicate = e -> e.getHiringDate().isEqual(finalParseHiredDate);
        break;

      default:
        System.out.println("Invalid entry.");
    }

    if (employeePredicate != null) {
      searchResult = employees.stream().filter(employeePredicate).collect(Collectors.toList());
    }

    return searchResult;
  }

  private String getValidEmployeeNumber() {
    String strEmpNumber;
    boolean validEmpNumber;

    do {
      System.out.print("Enter Number: ");
      strEmpNumber = input.next();
      validEmpNumber = isValidEmployeeNumber(strEmpNumber);
    } while (!validEmpNumber);
    return strEmpNumber;

  }

  private LocalDate getValidHireDate() {
    String inputHiringDate;
    boolean validHiringDate;
    LocalDate parseHiredDate = null;
    do {
      System.out.print("Enter Date Hired (yyyy-MM-dd): ");
      inputHiringDate = input.next();
      validHiringDate = isValidDate(inputHiringDate);
      if (!validHiringDate) {
        System.out.println("Invalid entry. Try again.");
      } else {
        parseHiredDate = parseStringDateToLocalDate(inputHiringDate);
        if (isFutureDate(parseHiredDate)) {
          validHiringDate = false;
          System.out.println("Invalid date. Future date is not allowed.");
        }
      }
    } while (!validHiringDate);
    return parseHiredDate;
  }

  private void printSearchAction() throws NoSuchFieldException {
    System.out.println("\nChoose an action");
    TypeAndRepeatingAnnotations typeAndRepeatingAnnotations = new TypeAndRepeatingAnnotations();
    Class<?> c = typeAndRepeatingAnnotations.getClass();
    Field fd = c.getDeclaredField("searchActions");
    Option[] searchActions = fd.getAnnotationsByType(Option.class);
    Arrays.stream(searchActions).forEach(searchAction -> System.out.println(searchAction.name()));
  }
}
