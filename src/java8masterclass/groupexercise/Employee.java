package java8masterclass.groupexercise;

import java.time.LocalDate;

public class Employee {

  public Employee(
      int employeeNumber,
      String firstName,
      String middleName,
      String lastName,
      LocalDate hiringDate) {
    this.employeeNumber = employeeNumber;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.hiringDate = hiringDate;
  }

  private int employeeNumber;

  private String firstName;

  private String middleName;

  private String lastName;

  private LocalDate hiringDate;

  public int getEmployeeNumber() {
    return employeeNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return getNonNullString(firstName)
        .concat(" " + getNonNullString(middleName))
        .concat(" " + getNonNullString(lastName));
  }

  public LocalDate getHiringDate() {
    return hiringDate;
  }

  private String getNonNullString(String value) {
    return value == null ? "" : value;
  }

}
