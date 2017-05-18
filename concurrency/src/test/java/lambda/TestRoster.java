package lambda;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lambda.Person.Sex;

public class TestRoster {

  // private Roster roster = new Roster();

  @Test
  public void testPrintPersonOlderThan() {
    // given
    List<Person> list = new ArrayList<Person>();
    Person p1 = mock(Person.class);
    given(p1.getAge()).willReturn(Integer.valueOf(31), Integer.valueOf(25), Integer.valueOf(32),
        Integer.valueOf(30), Integer.valueOf(18));
    list.add(p1);
    list.add(p1);
    list.add(p1);
    list.add(p1);
    list.add(p1);
    int age = 30;

    // when
    int count = Roster.printPersonOlderThan(list, age);

    // then
    assertThat(count, equalTo(2));
  }

  @Test
  public void testPrintPersonWithPredicate() {
    // given
    List<Person> roster = new ArrayList<Person>();
    Person p1 = mock(Person.class);
    given(p1.getGendar()).willReturn(Sex.FEMALE, Sex.MALE, Sex.FEMALE, Sex.MALE, Sex.FEMALE);
    given(p1.getAge()).willReturn(Integer.valueOf(21), Integer.valueOf(25), Integer.valueOf(32),
        Integer.valueOf(30), Integer.valueOf(18));
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);
    roster.add(p1);

    //when
    int count = Roster.printPersonWithPredicate(roster,
        (Person p) -> p.getGendar() == Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25);

    //then
    assertThat(count, equalTo(1));
  }
}
