package concurrency;

public class Counter {
  private int count = 0;

  public int getCount() {
    return count;
  }

  public void incrementCount() {
    this.count++;
    System.out.println("count++ = " + count);
  }
  
  public void decrementCount() {
    this.count--;
    System.out.println("count-- = " + count);
  }
  
  
}
