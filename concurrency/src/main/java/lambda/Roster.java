package lambda;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Roster {

  public static void main(String[] args) {


  }

  public static int printPersonOlderThan(Collection<Person> list, int age) {
    int count = 0;
    for (Person p : list) {
      System.out.println(p.getName() + ":" + p.getGendar() + ":" + p.getAge());
      if (p.getAge() > age) {
        count++;
      }
    }
    return count;
  }

  public static int printPersonWithPredicate(Collection<Person> roster, Predicate<Person> tester) {
    int count = 0;

    for (Person p : roster) {
      if (tester.test(p)) {
        count++;
      }
    }

    return count;
  }

  public static String processPersonWithFunction(List<Person> roster, Predicate<Person> tester,
      Function<Person, String> mapper, Consumer<String> block) {
    String ret = null;
    
    for(Person p: roster) {
      if (tester.test(p)) {
        ret = mapper.apply(p);
        block.accept(ret);
      }
    }
    return ret;
  }
  
  public static <P, S> S processPersonWithGenerics(Collection<P> collection, Predicate<P> filter, 
      Function<P, S> function, Consumer<S> consumer) {
    S ret = null;
    for (P p : collection) {
      if (filter.test(p)) {
        ret = function.apply(p);
        consumer.accept(ret);
      }
    }

    return ret;
  }
}
