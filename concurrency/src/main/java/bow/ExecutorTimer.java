package bow;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecutorTimer implements Runnable {
  private final static Logger logger = LogManager.getLogger(ExecutorTimer.class);
  private static final long start = System.currentTimeMillis();
  private long time;
  private ExecutorService pool;

  public ExecutorTimer(int second) {
    this.time = second * 1000;
  }

  public void register(ExecutorService service) {
    this.pool = service;
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
    shutdown();
  }

  private void shutdown() {
    pool.shutdown();
    List<Runnable> list = pool.shutdownNow();
    for(Runnable r: list) {
      logger.debug("" + r);
    }
  }
}
