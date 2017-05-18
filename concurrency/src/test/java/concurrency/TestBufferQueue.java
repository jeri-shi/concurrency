package concurrency;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import producerconsumer.BufferQueue;

public class TestBufferQueue {

  @Rule
  public Timeout globalTimeout = Timeout.seconds(2);

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void putString() {
    BufferQueue queue = new BufferQueue();
    try {
      queue.put("Test1");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertThat(queue.toString(), containsString("Test1"));
  }

  @Test(timeout = 1500)
  public void testLongTime() {
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(130);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testException() {
    thrown.expect(NullPointerException.class);
    String s = null;

    if (s.equals("S")) {
      
    }
    
    if ("S".equals(null)) {
      if (null instanceof Object) {
        System.out.println(" d");
      }
    }
  }


}
