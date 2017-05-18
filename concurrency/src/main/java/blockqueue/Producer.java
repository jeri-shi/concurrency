package blockqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {
  private final static Logger logger = LogManager.getLogger(Producer.class);

  private final BlockingQueue<String> queue;
  private String[] talks;

  public Producer(BlockingQueue<String> queue) {
    this.queue = queue;
  }

  public void setTalks(String[] talks) {
    this.talks = talks;
  }

  public void run() {
    Random random = new Random();
    int count = 0;
    try {
      while (true) {
        for (String talk : talks) {
          logger.debug(
              "Producer[" + Thread.currentThread().getName() + "] put '" + talk + "' in the queue");
          queue.put(talk);
          count++;
          Thread.sleep(random.nextInt(100));
        }
      }
    } catch (InterruptedException e) {
      logger.debug("Producer[" + Thread.currentThread().getName() + "] was interrupted.");
    } finally {
      logger.debug("Producer[" + Thread.currentThread().getName() + "] finished its tasks, put ["
          + count + "] talks in the queue");
    }
  }

}
