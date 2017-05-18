package producerconsumer;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {
  private static final Logger logger = LogManager.getLogger(Producer.class);

  private QueueInterface queue;
  private String[] talks;
  private final String name;

  public Producer(String name) {
    this.name = name;
  }

  public void setQueue(QueueInterface queue) {
    this.queue = queue;
  }

  public void setTalks(String[] talks) {
    this.talks = talks;
  }

  public void run() {
    logger.trace("Producer is running...");
    int count = 0;
    try {
      while (true) {
        for (String talk : talks) {
          queue.put(talk);
          count++;
          Thread.sleep(ThreadLocalRandom.current().nextInt(100));
        }
        // queue.put(" --- " + i + " --- ");
      }
//      queue.put("End");
//      queue.put("End");
//      queue.put("End");
    } catch (InterruptedException e) {
      logger.warn("Producer " + name + " is interruped.");
    } finally {
      logger.info("Producer[" + name + "][" + Thread.currentThread().getName() + "] put " + count
          + " talks.");
    }

  }


}
