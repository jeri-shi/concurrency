package lambda;

import java.time.LocalDate;
import java.time.Period;

public class Person {

  public enum Sex {
    MALE, FEMALE
  }

  private String name;
  private LocalDate birthday;
  private Sex gendar;
  private String emailAddress;

  public Person(String name, LocalDate birthday, Sex gendar, String email) {
    this.name = name;
    this.birthday = birthday;
    this.gendar = gendar;
    this.emailAddress = email;
  }

  public LocalDate getBirthday() {
    return this.birthday;
  }

  public String getName() {
    return name;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public Sex getGendar() {
    return gendar;
  }

  public int getAge() {
    LocalDate current = LocalDate.now();
    Period period = Period.between(birthday, current);
    return period.getYears();
    // Duration duration = Duration.between(birthday, current);
    // return (int) duration.toDays() / 365;
  }

  public String printPerson() {
    return this.toString();
  }

  public static int compareAge(Person a, Person b) {
    return a.getBirthday().compareTo(b.getBirthday());
  }

  @Override
  public String toString() {
    return name + "[" + gendar + "][" + getAge() + "][" + emailAddress + "]";
  }

}
