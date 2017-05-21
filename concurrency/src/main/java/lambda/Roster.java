package lambda;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lambda.Person.Sex;

public class Roster {
  private static final Logger logger = LogManager.getLogger(Roster.class);
  
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

    for (Person p : roster) {
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

  public static void printPersonNames(Collection<Person> roster) {
    roster.stream().forEach(p -> System.out.println(p));
    logger.info("--");
    roster.stream().filter(p -> p.getGendar() == Sex.FEMALE).forEach(p -> logger.info(p));
  }
  
  public static double getAverageAge(Collection<Person> roster, Predicate<Person> filter) {
    return roster.stream().filter(filter).mapToInt(Person::getAge).average().getAsDouble();
  }

  public static <P, S> void processElement(Collection<P> collection, Predicate<P> filter,
      Function<P, S> mapper, Consumer<S> block) {
    collection.stream().filter(filter).map(mapper).forEach(block);
  }


}
