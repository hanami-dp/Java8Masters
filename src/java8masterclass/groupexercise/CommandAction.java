package java8masterclass.groupexercise;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.IsoEra;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static java.time.format.DateTimeFormatter.ofPattern;

@FunctionalInterface
public interface CommandAction {
  Scanner input = new Scanner(System.in);

  DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("MMM dd, yyyy");

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
    DateTimeFormatter dateParser =
        new DateTimeFormatterBuilder()
            .appendPattern("yyyyMMdd")
            .parseDefaulting(ChronoField.ERA, IsoEra.CE.getValue())
            .toFormatter(Locale.ROOT)
            .withResolverStyle(ResolverStyle.STRICT);

    try {
      LocalDate.parse(value, dateParser);
    } catch (DateTimeParseException dtpe) {
      isValidDate = false;
    }
    return isValidDate;
  }

  default String formatDate(String date) {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        .format(DATE_TIME_FORMATTER);
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
                  formatDate(e.getHiringDate().toString())));
    }

    System.out.println(
        "===============================================================================\n");
  }
}
