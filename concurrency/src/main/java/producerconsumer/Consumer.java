package producerconsumer;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {

  private static final Logger logger = LogManager.getLogger(Consumer.class);
  private QueueInterface queue;
  private int count;
  private String name;

  public Consumer(String name) {
    this.name = name;
  }

  public void setQueue(QueueInterface queue) {
    this.queue = queue;
  }

  public void run() {
    logger.trace("Consumer is running...");
    String talk = null;
    try {
      while (!"End".equals(talk = queue.take())) {
        count++;
        logger.info(talk + " ");
        Thread.sleep(ThreadLocalRandom.current().nextInt(150));
      }
    } catch (InterruptedException e) {
      logger.warn("Consumer " + name + " is interruped.");
    }
    logger.trace("Consumer " + Thread.currentThread().getName() + " is end.");
    logger.info("Consumer["+name+"]["+Thread.currentThread().getName()+"] consumed " + count + " talks.");

  }

}
