package producerconsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bow.Timer;


public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    String[] talks = {"Hello", "World!", "How", "are", "you", "doing?"};
    String[] talks1 = {"Hello - 1", "World! - 1", "How - 1", "are - 1", "you - 1", "doing? - 1"};
    Producer producer = new Producer("p");
    Producer producer1 = new Producer("p1");
    Consumer consumer = new Consumer("c");
    Consumer consumer2 = new Consumer("c2");
    Consumer consumer3 = new Consumer("c3");
    Consumer consumer4 = new Consumer("c4");

    QueueInterface queue = new BufferQueue();

    producer.setQueue(queue);
    producer1.setQueue(queue);
    consumer.setQueue(queue);
    consumer2.setQueue(queue);
    consumer3.setQueue(queue);
    consumer4.setQueue(queue);

    producer.setTalks(talks);
    producer1.setTalks(talks1);

    Thread p = new Thread(producer);
    Thread p1 = new Thread(producer1);
    Thread c = new Thread(consumer);
    Thread c2 = new Thread(consumer2);
    Thread c3 = new Thread(consumer3);
    Thread c4 = new Thread(consumer4);

    Timer timer = new Timer(80);
    timer.register(p);
    timer.register(p1);
    timer.register(c);
    timer.register(c2);
    timer.register(c3);
    timer.register(c4);
    Thread t = new Thread(timer);
    
    p.start();
    c.start();
    c2.start();
    t.start();
    c3.start();
    c4.start();
    p1.start();
    
    try{
      p.join();
      p1.join();
      c.join();
      c2.join();
      c3.join();
      c4.join();
      t.join();
      
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    
    logger.info("End");
/*    try {
      while (p.isAlive() || c.isAlive() || c2.isAlive() || c3.isAlive()) {
        p.join(250);
        c.join(250);
        c2.join(250);
//        c3.join(250);
        logger.debug("Time is spending " + (System.currentTimeMillis() - begin) / 1000 + ",p:"
            + p.getName() + ":" + p.isAlive() + ",c:" + c.getName() + ":" + c.isAlive() + ",c2:"
            + c2.getName() + ":" + c2.isAlive() + ",c3:" + c3.getName() + ":" + c3.isAlive());
        logger.debug(c2.isInterrupted());
        if (System.currentTimeMillis() - begin > 15 * 1000) {
          if (p.isAlive()) p.interrupt();
          if (c.isAlive()) c.interrupt();
          if (c2.isAlive()) c2.interrupt();
//          if (c3.isAlive()) c3.interrupt();
        }
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
*/

  }

}
