package java8masterclass.groupexercise.optionsimpl;

import java8masterclass.groupexercise.CommandAction;
import java8masterclass.groupexercise.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadFromFile implements CommandAction {

  private static final String INVALID_INPUT_MESSAGE = "Invalid %s at row";

  private static final String INCOMPLETE_INPUT_MESSAGE = "Invalid/incomplete data at row";

  @Override
  public void doAction(List<Employee> employees) {
    boolean isFileValid;
    String fileName;
    List<Employee> importedEmployees = new ArrayList<>();
    do {
      AtomicInteger row = new AtomicInteger(1);
      System.out.print("\nEnter filename: ");
      fileName = input.next();

      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String[] data = br.lines().toArray(String[]::new);

        if (data.length <= 1) {
          System.out.printf("No records to be imported from file %s.%n%n%n", fileName);
          isFileValid = true;
        } else {
          List<Employee> finalImportedEmployees = importedEmployees;
          Arrays.stream(data)
              .skip(1)
              .forEach(
                  d -> {
                    row.getAndIncrement();
                    if (!d.isBlank()) {
                      String[] employeeData = d.split(",");
                      finalImportedEmployees.add(
                          createEmployee(employeeData, finalImportedEmployees, employees));
                    }
                  });

          employees.addAll(finalImportedEmployees);
          isFileValid = true;
          System.out.printf("Records successfully imported from file %s.%n%n%n", fileName);
        }
      } catch (IOException e) {
        isFileValid = false;
        importedEmployees = new ArrayList<>();
        System.out.printf("Error encountered reading file %s.", fileName);
      } catch (IllegalArgumentException iae) {
        isFileValid = false;
        importedEmployees = new ArrayList<>();
        System.out.printf(
            "Error encountered reading file %s. %s %d.", fileName, iae.getMessage(), row.get());
      }

    } while (!isFileValid);
  }

  private Employee createEmployee(
      String[] employeeData, List<Employee> importedEmployees, List<Employee> currentEmployees) {
    return new Employee(
        getEmployeeNumber(employeeData, importedEmployees, currentEmployees),
        getEmployeeName(employeeData, "first name", 1),
        getEmployeeName(employeeData, "middle name", 2),
        getEmployeeName(employeeData, "last name", 3),
        getHiringDate(employeeData));
  }

  private LocalDate getHiringDate(String[] employeeData) {
    LocalDate hiredDate;
    try {
      String concatenatedDate = employeeData[4].trim() + "," + employeeData[5];
      hiredDate = LocalDate.parse(concatenatedDate, DATE_TIME_FORMATTER_MMMDDYYYY);
      if (isFutureDate(hiredDate)) {
        throw new IllegalArgumentException(
            "Invalid date. Future date is not allowed. Found at row");
      }
    } catch (DateTimeParseException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(String.format(INCOMPLETE_INPUT_MESSAGE));
    }
    return hiredDate;
  }

  private int getEmployeeNumber(
      String[] employeeData, List<Employee> importedEmployees, List<Employee> currentEmployees) {
    int employeeNumber;
    try {
      employeeNumber = Integer.parseInt(employeeData[0]);
      if (isEmployeeNumberAlreadyExists(employeeNumber, importedEmployees, currentEmployees)) {
        throw new IllegalArgumentException(
            "Invalid employee number. Employee number already exists. Found at row");
      }
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException(String.format(INVALID_INPUT_MESSAGE, "employee number"));
    } catch (IndexOutOfBoundsException ioobe) {
      throw new IllegalArgumentException(String.format(INCOMPLETE_INPUT_MESSAGE));
    }
    return employeeNumber;
  }

  private boolean isEmployeeNumberAlreadyExists(
      int employeeNumber, List<Employee> importedEmployees, List<Employee> currentEmployees) {
    return importedEmployees.stream().anyMatch(e -> e.getEmployeeNumber() == employeeNumber)
        || currentEmployees.stream().anyMatch(e -> e.getEmployeeNumber() == employeeNumber);
  }

  private String getEmployeeName(String[] employeeData, String namePart, int indexEmpData) {
    String name;
    try {
      name = employeeData[indexEmpData];
      if (Objects.isNull(name) || name.isBlank()) {
        throw new IllegalArgumentException(String.format(INVALID_INPUT_MESSAGE, namePart));
      }
    } catch (IndexOutOfBoundsException ioobe) {
      throw new IllegalArgumentException(String.format(INCOMPLETE_INPUT_MESSAGE));
    }
    return name.trim();
  }
}
