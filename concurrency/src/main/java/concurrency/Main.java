package concurrency;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello World!");
    Counter count = new Counter();
    
    Thread t1 = new Thread(new ThreadX(count, -1));
    Thread t2 = new Thread(new ThreadX(count, 1));
    
    
    t1.start();
    t2.start();
    
    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
