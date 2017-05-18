package bow;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Timer implements Runnable {
  private final static Logger logger = LogManager.getLogger(Timer.class);
  private static final long start = System.currentTimeMillis();
  private long time;
  private List<Thread> list = new ArrayList<Thread>();

  public Timer(int second) {
    this.time = second * 1000;
  }

  public void register(Thread thread) {
    list.add(thread);
  }

  public void run() {
    try {
      do {
        logger.info("---------------------------------------------------" + (System.currentTimeMillis() - start)/1000 + "sec is passed.");
        Thread.sleep(1000);
      } while (System.currentTimeMillis() - start < time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    logger.info("---------------------------------------------------" + (System.currentTimeMillis() - start)/1000 + "sec is passed.");
    logger.info("time is over...");
    for (Thread t : list) {
      if (t.isAlive()) {
        logger.info("Thread " + t.getName() + " was interrupting...");
        t.interrupt();
      }
    }

  }

}
