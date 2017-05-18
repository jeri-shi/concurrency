package blockqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {
  private final static Logger logger = LogManager.getLogger(Consumer.class);

  private final BlockingQueue<String> queue;

  public Consumer(BlockingQueue<String> queue) {
    this.queue = queue;
  }

  public void run() {
    String talk = null;
    int count = 0;
    Random random = new Random();
    try {
      while (true) {
        talk = queue.take();
        logger.debug("Consumer[" + Thread.currentThread().getName() + "] take '" + talk
            + "' from the queue");
        logger.info(talk);
        count++;
        Thread.sleep(random.nextInt(150));
      }
    } catch (InterruptedException e) {
      logger.debug("Consumer[" + Thread.currentThread().getName() + "] was interrupted.");
    } finally {
      logger.debug("Consumer[" + Thread.currentThread().getName() + "] finished its tasks, took ["
          + count + "] from queue.");
    }
  }

}
