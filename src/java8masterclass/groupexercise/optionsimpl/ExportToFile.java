package java8masterclass.groupexercise.optionsimpl;

import java8masterclass.groupexercise.CommandAction;
import java8masterclass.groupexercise.Employee;
import java8masterclass.groupexercise.Option;
import java8masterclass.groupexercise.TypeAndRepeatingAnnotations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExportToFile implements CommandAction {

  @Override
  public void doAction(List<Employee> employees) throws NoSuchFieldException {
    printEncodingAction();

    boolean isValidSelection;
    do {
      System.out.print("\nSelect action: ");
      int encodingSelection = input.nextInt();
      isValidSelection = isValidSelection(encodingSelection);
      if (isValidSelection) {
        if (encodingSelection == -1) {
          System.out.println();
        } else if (encodingSelection == 1 || encodingSelection == 2) {
          AtomicBoolean success = new AtomicBoolean(false);
          do {
            System.out.print("Enter Filename: ");
            String fileName = input.next();
            boolean isFileExists = isFileExists(fileName);
            boolean isEncoded = encodingSelection == 1;

            try (BufferedWriter writer = getWriter(fileName, isFileExists)) {

              executeWriteToFile(
                      employees, encodingSelection, success, fileName, isFileExists, isEncoded, writer);

              System.out.printf("Records exported successfully to file %s.%n%n", fileName);
              success.set(true);
            } catch (IOException e) {
              success.set(false);
              System.out.printf("Exporting of file %s has failed.%n%n", fileName);
            }
          } while (!success.get());
        }
      } else {
        System.out.println("Invalid entry. Try again.");
      }

    } while (!isValidSelection);
  }

  private boolean isValidSelection(int encodingSelection) {
    return encodingSelection == -1 || encodingSelection == 1 || encodingSelection == 2;
  }

  private BufferedWriter getWriter(String fileName, boolean isFileExists) throws IOException {
    return isFileExists
        ? new BufferedWriter(new FileWriter(fileName, true))
        : new BufferedWriter(new FileWriter(fileName));
  }

  private boolean isFileExists(String fileName) {
    File f = new File(fileName);
    return f.exists() && !f.isDirectory();
  }

  private void executeWriteToFile(
      List<Employee> employees,
      int encodingSelection,
      AtomicBoolean success,
      String fileName,
      boolean isFileExists,
      boolean isEncoded,
      BufferedWriter writer)
      throws IOException {

    if (encodingSelection == 2 && !isFileExists) {
      writer.write(String.format("%-25s %-30s %-25s%n", "Employee Number", "Name", "Date Hired"));
    }
    employees.stream()
        .forEach(
            employee -> {
              try {
                String employeeInfo =
                    String.format(
                        "%-25s %-30s %-25s%n",
                        employee.getEmployeeNumber(),
                        employee.getFullName(),
                        formatDate(employee.getHiringDate().toString()));
                if (isEncoded) {
                  writer.write(Base64.getEncoder().encodeToString(employeeInfo.getBytes()));
                } else {
                  writer.write(employeeInfo);
                }
              } catch (IOException e) {
                success.set(false);
                System.out.printf("Exporting of file %s has failed.%n%n", fileName);
              }
            });
  }

  private void printEncodingAction() throws NoSuchFieldException {
    System.out.println("\nChoose an action");
    TypeAndRepeatingAnnotations typeAndRepeatingAnnotations = new TypeAndRepeatingAnnotations();
    Class<?> c = typeAndRepeatingAnnotations.getClass();
    Field fd = c.getDeclaredField("encodingActions");
    Option[] encodingActions = fd.getAnnotationsByType(Option.class);
    Arrays.stream(encodingActions).forEach(sortAction -> System.out.println(sortAction.name()));
  }
}
