package bow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorMain {

  public static void main(String[] args) {
    ExecutorService service = Executors.newFixedThreadPool(4);
    
    Friend shijin = new Friend("Shijin");
    Friend maria = new Friend("Maria");
    Friend amos = new Friend("Amos");
    
    BowLoop bl1 = new BowLoop(shijin, maria);
    BowLoop bl2 = new BowLoop(maria, amos);
    BowLoop bl3 = new BowLoop(amos, shijin);
    
    ExecutorTimer timer = new ExecutorTimer(3);
    timer.register(service);
    
    service.execute(bl1);
    service.execute(bl2);
    service.execute(bl3);
    service.execute(timer);
//    
//    service.shutdown();
    
  }

}
