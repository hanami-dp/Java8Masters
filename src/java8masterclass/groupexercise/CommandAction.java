package java8masterclass.groupexercise;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static java.time.format.DateTimeFormatter.ofPattern;

@FunctionalInterface
public interface CommandAction {
  Scanner input = new Scanner(System.in);

  DateTimeFormatter DATE_TIME_FORMATTER_MMMDDYYYY = ofPattern("MMM dd, yyyy");
  DateTimeFormatter DATE_TIME_FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  void doAction(List<Employee> employees) throws NoSuchFieldException;

  default boolean isValidEmployeeNumber(String value) {
    boolean isValidEmployeeNumber = true;
    try {
      Integer.valueOf(value);
    } catch (NumberFormatException nfe) {
      isValidEmployeeNumber = false;
      System.out.println("Invalid entry. Try again.");
    }
    return isValidEmployeeNumber;
  }

  default boolean isValidDate(String value) {
    boolean isValidDate = true;
    DateTimeFormatter dateParser = DATE_TIME_FORMATTER_YYYYMMDD;

    try {
      LocalDate.parse(value, dateParser);
    } catch (DateTimeParseException dtpe) {
      isValidDate = false;
    }
    return isValidDate;
  }

  default String formatDateMMMddyyyy(String date) {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        .format(DATE_TIME_FORMATTER_MMMDDYYYY);
  }

  default boolean isFutureDate(LocalDate inputDate) {
    LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
    return inputDate.isAfter(localDate);
  }

  default LocalDate parseStringDateToLocalDate(String employeeHiringDate) {
    return LocalDate.parse(employeeHiringDate, DATE_TIME_FORMATTER_YYYYMMDD);
  }

  default void printEmployees(List<Employee> employees) {
    System.out.println(
        "===============================================================================");
    System.out.printf("%-25s %-30s %-25s%n", "EmployeeNumber", "Name", "Date Hired");
    System.out.println(
        "===============================================================================");

    if (employees == null || employees.isEmpty()) {
      System.out.printf("%25s %s %n", "", "No records found.");
    } else {
      employees.forEach(
          e ->
              System.out.printf(
                  "%-25s %-30s %-25s%n",
                  e.getEmployeeNumber(),
                  e.getFullName(),
                  formatDateMMMddyyyy(e.getHiringDate().toString())));
    }

    System.out.println(
        "===============================================================================\n");
  }
}
