package java8masterclass.groupexercise.optionsimpl;

import java8masterclass.groupexercise.CommandAction;
import java8masterclass.groupexercise.Employee;
import java8masterclass.groupexercise.Option;
import java8masterclass.groupexercise.TypeAndRepeatingAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListAllEmployees implements CommandAction {

  @Override
  public void doAction(List<Employee> employees) throws NoSuchFieldException {
    printSortAction();
    boolean validSortSelection;
    do {
      System.out.print("\nSelect action: ");
      int sortSelection = input.nextInt();
      validSortSelection = isValidSortSelection(sortSelection);

      if (validSortSelection) {
        if (sortSelection == -1) {
          System.out.println();
        } else {
          printOrderByAction();
          boolean validOrderBySelection;

          do {
            System.out.print("\nSelect action: ");
            int orderBySelection = input.nextInt();
            validOrderBySelection = isValidOrderBySelection(orderBySelection);

            if (validOrderBySelection) {
              if (orderBySelection == -1) {
                System.out.println();
                validSortSelection = false;
                printSortAction();
              } else {
                employees = sort(employees, sortSelection, orderBySelection);
                printEmployees(employees);
              }

            } else {
              System.out.println("Invalid entry. Try again.");
            }
          } while (!validOrderBySelection);
        }
      } else {
        System.out.println("Invalid entry. Try again.");
      }
    } while (!validSortSelection);
  }

  private List<Employee> sort(List<Employee> employees, int sortSelection, int orderBySelection) {

    Comparator<Employee> employeeComparator = null;
    List<Employee> sorted;
    switch (sortSelection) {
      case 1:
        employeeComparator = Comparator.comparing(Employee::getEmployeeNumber);
        break;
      case 2:
        employeeComparator = Comparator.comparing(Employee::getFirstName);
        break;
      case 3:
        employeeComparator = Comparator.comparing(Employee::getLastName);
        break;
      case 4:
        employeeComparator = Comparator.comparing(Employee::getHiringDate);
        break;
      default:
        System.out.println("Displaying employees without sorting.");
    }

    if (employeeComparator != null) {
      if (orderBySelection == 2) {
        sorted =
            employees.stream().sorted(employeeComparator.reversed()).collect(Collectors.toList());
      } else {
        sorted = employees.stream().sorted(employeeComparator).collect(Collectors.toList());
      }
    } else {
      sorted = employees;
    }
    return sorted;
  }

  private void printSortAction() throws NoSuchFieldException {
    System.out.println("\nChoose an action");
    TypeAndRepeatingAnnotations typeAndRepeatingAnnotations = new TypeAndRepeatingAnnotations();
    Class<?> c = typeAndRepeatingAnnotations.getClass();
    Field fd = c.getDeclaredField("sortActions");
    Option[] sortActions = fd.getAnnotationsByType(Option.class);
    Arrays.stream(sortActions).forEach(sortAction -> System.out.println(sortAction.name()));
  }

  private void printOrderByAction() throws NoSuchFieldException {
    System.out.println("\nChoose an action");
    TypeAndRepeatingAnnotations typeAndRepeatingAnnotations = new TypeAndRepeatingAnnotations();
    Class<?> c = typeAndRepeatingAnnotations.getClass();
    Field fd = c.getDeclaredField("orderByActions");
    Option[] orderByActions = fd.getAnnotationsByType(Option.class);
    Arrays.stream(orderByActions)
        .forEach(orderByAction -> System.out.println(orderByAction.name()));
  }

  private boolean isValidSortSelection(int sortingSelection) {
    return sortingSelection == -1
        || sortingSelection == 1
        || sortingSelection == 2
        || sortingSelection == 3
        || sortingSelection == 4;
  }

  private boolean isValidOrderBySelection(int orderBySelection) {
    return orderBySelection == -1 || orderBySelection == 1 || orderBySelection == 2;
  }
}
