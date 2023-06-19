package java8masterclass.groupexercise;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class EmployeeProgram {

    private static Option[] mainOptions;

    private static CommandActionSupplier mainOptionSupplier;

    private static List<Employee> employees;

    private static CommandAction exit;

    public static void main(String[] args) throws NoSuchFieldException {
        init();
        Scanner input = new Scanner(System.in);
        Integer selection = null;
        do {
            printMainOptions();

            boolean validAction;
            do {
                try {
                    System.out.print("\nEnter action type: ");
                    String strSelection = input.next();
                    selection = Integer.valueOf(strSelection);
                    if (selection == -1) {
                        exit.doAction(employees);
                    }
                    validAction = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid entry. Try again.");
                    validAction = false;
                }
            } while (!validAction);

            CommandAction commandAction = mainOptionSupplier.apply(selection);
            if (Objects.isNull(commandAction)) {
                System.out.println("Invalid entry. Try again.\n");
            } else {
                commandAction.doAction(employees);
            }
        } while (selection != -1);

    }

    private static void init() throws NoSuchFieldException {
        TypeAndRepeatingAnnotations typeAndRepeatingAnnotations = new TypeAndRepeatingAnnotations();
        Class<?> c = typeAndRepeatingAnnotations.getClass();
        Field fd = c.getDeclaredField("mainOptions");
        mainOptions = fd.getAnnotationsByType(Option.class);
        mainOptionSupplier = new CommandActionSupplier();
        employees = new ArrayList<>();

        exit = (employees) -> {
            System.out.println("Goodbye!");
            System.exit(0);
        };
    }

    private static void printMainOptions() {
        System.out.println("Main Options");
        Arrays.stream(mainOptions).forEach(mainOption -> System.out.println(mainOption.name()));
    }
}
