package lambda;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lambda.Person.Sex;

public class TestRoster {

  private Collection<Person> personList;

  @Before
  public void init() {
    personList = new LinkedList<Person>();
    personList
        .add(new Person("Zhang Shan", LocalDate.of(2001, 2, 3), Sex.FEMALE, "zhangsan@sss.com"));
    personList.add(new Person("Li Si", LocalDate.of(1988, 9, 22), Sex.MALE, "lisi@sss.com"));
    personList.add(new Person("Wang wu", LocalDate.of(1990, 12, 30), Sex.FEMALE, "wangwu@sss.com"));
    personList.add(new Person("Zhao Liu", LocalDate.of(1983, 10, 3), Sex.MALE, "zhaoliu@sss.com"));
    personList.add(new Person("Zhu ba", LocalDate.of(1977, 3, 23), Sex.FEMALE, "zhuba@sss.com"));
  }

  @Test
  public void testPrintPersonOlderThan() {
    // given
    // List<Person> list = new ArrayList<Person>();
    Collection<Person> list = personList;

    /*
     * Person p1 = mock(Person.class); given(p1.getAge()).willReturn(Integer.valueOf(31),
     * Integer.valueOf(25), Integer.valueOf(32), Integer.valueOf(30), Integer.valueOf(18));
     * list.add(p1); list.add(p1); list.add(p1); list.add(p1); list.add(p1);
     */
    int age = 30;

    // when
    int count = Roster.printPersonOlderThan(list, age);

    // then
    assertThat(count, equalTo(2));
  }

  @Test
  public void testPrintPersonWithPredicate() {
    // given
    /*
     * List<Person> roster = new ArrayList<Person>(); Person p1 = mock(Person.class);
     * given(p1.getGendar()).willReturn(Sex.FEMALE, Sex.MALE, Sex.FEMALE, Sex.MALE, Sex.FEMALE);
     * given(p1.getAge()).willReturn(Integer.valueOf(21), Integer.valueOf(25), Integer.valueOf(32),
     * Integer.valueOf(30), Integer.valueOf(18)); roster.add(p1); roster.add(p1); roster.add(p1);
     * roster.add(p1); roster.add(p1);
     */

    Collection<Person> roster = personList;
    // when
    int count = Roster.printPersonWithPredicate(roster,
        (Person p) -> p.getGendar() == Sex.FEMALE && p.getAge() >= 18 && p.getAge() <= 26);

    // then
    assertThat(count, equalTo(1));
  }

  @Test
  public void testProcessPersonWithFunction() {
    // given
    List<Person> roster = new ArrayList<Person>();
    Person p1 = mock(Person.class);
    given(p1.getGendar()).willReturn(Sex.FEMALE, Sex.MALE, Sex.FEMALE, Sex.MALE, Sex.FEMALE);
    given(p1.getAge()).willReturn(Integer.valueOf(21), Integer.valueOf(25), Integer.valueOf(32),
        Integer.valueOf(30), Integer.valueOf(18));
    given(p1.printPerson()).willReturn("return");
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);

    // when
    String emailString = Roster.processPersonWithFunction(roster,
        (Person p) -> p.getGendar() == Sex.FEMALE && p.getAge() >= 18 && p.getAge() <= 35,
        p -> p.printPerson(), email -> System.out.println(email));

    // then
    assertThat(emailString, equalTo("return"));

  }

  @Test
  public void testProcessPersonWithGenerics() {
    // given
    Collection<Person> roster = new LinkedList<Person>();
    Person p1 = mock(Person.class);
    given(p1.getGendar()).willReturn(Sex.FEMALE, Sex.MALE, Sex.FEMALE, Sex.MALE, Sex.FEMALE);
    given(p1.getAge()).willReturn(Integer.valueOf(21), Integer.valueOf(25), Integer.valueOf(32),
        Integer.valueOf(30), Integer.valueOf(18));
    given(p1.printPerson()).willReturn("return");
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);

    // when
    String strGendar = Roster.<Person, String>processPersonWithGenerics(roster,
        (Person p) -> p.getGendar() == Sex.MALE && p.getAge() > 20 && p.getAge() < 30,
        (Person p) -> p.getGendar().name(), gendar -> System.out.println(gendar + "hh"));

    // then
    assertThat(strGendar, equalTo("FEMALE"));
  }

  @Test
  public void testProcessElement() {
    // given personList is ready.

    // when
    Roster.processElement(personList, (Person p) -> p.getGendar() == Sex.FEMALE && p.getAge() > 30,
        (Person p) -> p.getName() + ":" + p.getGendar() + ":" + p.getAge(), (String person) -> System.out.println(person));

    // then

  }

}
