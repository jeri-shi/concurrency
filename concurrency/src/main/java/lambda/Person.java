package lambda;

import java.time.LocalDate;

public class Person {
  
  public enum Sex {
    MALE, FEMALE
  }
  
  String name;
  LocalDate birthday;
  Sex gendar;
  String emailAddress;
  
  public int getAge() {
    return 0;
  }
  
  public Sex getGendar() {
    return Sex.MALE;
  }
  public void printPerson() {
    
  }

}
