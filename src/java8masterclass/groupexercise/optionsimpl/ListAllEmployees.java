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

    private Option[] sortActions;

    @Override
    public void doAction(List<Employee> employees) throws NoSuchFieldException {
        printSortAction();
        System.out.print("\nSelect action: ");
        int sortSelection = input.nextInt();
        if (sortSelection == -1) {
            System.out.println();
        } else {
            System.out.println("Choose an action");


            employees = sort(employees, sortSelection);
            printEmployees(employees);
        }
    }

    private List<Employee> sort(List<Employee> employees, int sortSelection) {

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
                System.out.println("Invalid choice. Displaying employees without sorting.");
        }

        if (employeeComparator != null) {
            sorted = employees.stream()
                    .sorted(employeeComparator).collect(Collectors.toList());
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
        sortActions = fd.getAnnotationsByType(Option.class);
        Arrays.stream(sortActions).forEach(sortAction -> System.out.println(sortAction.name()));
    }
}
