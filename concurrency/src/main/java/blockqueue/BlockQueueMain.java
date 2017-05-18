package blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BlockQueueMain {

  public static void main(String[] args) {
    String[] talks = {"Hello", "World!", "How", "are", "you", "doing?"};
    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);
    Producer producer = new Producer(queue);
    Consumer consumer = new Consumer(queue);
    Consumer c2 = new Consumer(queue);
    
    
    producer.setTalks(talks);
    
    ExecutorService pool = Executors.newFixedThreadPool(10);
    pool.execute(consumer);
    pool.execute(producer);
    pool.execute(c2);
    

    try {
      pool.awaitTermination(10, TimeUnit.SECONDS);
      pool.shutdown();
      pool.shutdownNow();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
