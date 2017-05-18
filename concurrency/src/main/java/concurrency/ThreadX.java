package concurrency;

public class ThreadX implements Runnable {

  private Counter count = null;
  private int flag = 0;

  public ThreadX(Counter count, int flag) {
    this.count = count;
    this.flag = flag;
  }

  public void run() {
    try {

      if (flag == 1) {
        for (int i = 0; i < 20; i++) {
          count.incrementCount();
          Thread.sleep((long)Math.random() * 200);
        }
      } else if (flag == -1) {
        for (int i = 0; i < 20; i++) {
          count.decrementCount();
          Thread.sleep((long)Math.random() * 200);
        }
      }
      print();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public void print() {
    System.out.println(count.getCount());

  }

}
