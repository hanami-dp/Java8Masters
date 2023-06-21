package java8masterclass.groupexercise;

import java8masterclass.groupexercise.optionsimpl.AddNewRecord;
import java8masterclass.groupexercise.optionsimpl.DeleteEmployeeRecord;
import java8masterclass.groupexercise.optionsimpl.ExportToFile;
import java8masterclass.groupexercise.optionsimpl.ListAllEmployees;
import java8masterclass.groupexercise.optionsimpl.ReadFromFile;
import java8masterclass.groupexercise.optionsimpl.SearchEmployeeRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandActionSupplier implements Function<Integer, CommandAction> {

  private Map<Integer, CommandAction> commandActionMap;

  public CommandActionSupplier() {
    this.commandActionMap = initCommandActionMap();
  }

  private Map<Integer, CommandAction> initCommandActionMap() {
    Map<Integer, CommandAction> commandActionMap = new HashMap<>();
    commandActionMap.put(1, new ListAllEmployees());
    commandActionMap.put(2, new AddNewRecord());
    commandActionMap.put(3, new DeleteEmployeeRecord());
    commandActionMap.put(4, new SearchEmployeeRecord());
    commandActionMap.put(5, new ReadFromFile());
    commandActionMap.put(6, new ExportToFile());

    return commandActionMap;
  }

  @Override
  public CommandAction apply(Integer integer) {
    return commandActionMap.get(integer);
  }
}
