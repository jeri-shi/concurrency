package lambda;

import java.util.List;
import java.util.function.Predicate;

public class Roster {

  public static void main(String[] args) {
    

  }

  public static int printPersonOlderThan(List<Person> list, int age) {
    int count = 0;
    for(Person p: list) {
      if (p.getAge() > age) {
        count++;
      }
    }
    return count;
  }
  
  public static int printPersonWithPredicate(List<Person> roster, Predicate<Person> tester) {
    int count = 0;
    
    for(Person p: roster) {
      if (tester.test(p)) {
        count++;
      }
    }
    
    return count;
  }
}
