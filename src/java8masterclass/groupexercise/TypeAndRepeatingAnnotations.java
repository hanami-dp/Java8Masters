package java8masterclass.groupexercise;

public class TypeAndRepeatingAnnotations {

    @Option(name="[1] List All Employee Records")
    @Option(name="[2] Add New Employee Record")
    @Option(name="[3] Delete Employee Record")
    @Option(name="[4] Search Employee Record")
    @Option(name="[-1] Exit")
    private String mainOptions;

    @Option(name="[1] Sorted By Employee Number")
    @Option(name="[2] Sorted By First Name")
    @Option(name="[3] Sorted By Last Name")
    @Option(name="[4] Sorted By Hiring Date")
    @Option(name="[-1] Back")
    private String sortActions;

    @Option(name="[1] Search By Employee Number")
    @Option(name="[2] Search By First Name")
    @Option(name="[3] Search By Middle Name")
    @Option(name="[4] Search By Last Name")
    @Option(name="[5] Search By Hiring Date")
    @Option(name="[-1] Back")
    private String searchActions;

}
