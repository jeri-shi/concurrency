package bow;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BowLoop implements Runnable {
  private final static Logger logger = LogManager.getLogger(BowLoop.class);
  private Friend bower;
  private Friend bowee;
  private Random random = new Random();
  
  public BowLoop(Friend bower, Friend bowee) {
    this.bower = bower;
    this.bowee = bowee;
  }
  
  
  
  public static void main(String[] args) {
    Friend shijin = new Friend("Shijin");
    Friend maria = new Friend("Maria");
    Friend amos = new Friend("Amos");
    
    BowLoop bl1 = new BowLoop(shijin, maria);
    BowLoop bl2 = new BowLoop(maria, amos);
    BowLoop bl3 = new BowLoop(amos, shijin);
    
    Thread t = new Thread(bl1);
    Thread t1 = new Thread(bl2);
    Thread t3 = new Thread(bl3);
    
    Timer timer = new Timer(3);
    timer.register(t);
    timer.register(t1);
    timer.register(t3);
    
    Thread t2 = new Thread(timer);
    
    t.start();
    t1.start();
    t2.start();
    t3.start();
   
    try {
      t.join();
      t1.join();
      t3.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    logger.info("----------------------------------------------------End");
    
    
  }



  public void run() {
    while(true) {
      bowee.bow(bower);
      logger.info("");
      try {
        Thread.sleep(random.nextInt(100));
      } catch (InterruptedException e) {
        logger.warn(Thread.currentThread().getName() + " was interrupted!");
        break;
      }
    }
    
  }

}
