package producerconsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Queue implements QueueInterface{
  private static final Logger logger = LogManager.getLogger(Queue.class);

  private String talk;

  public synchronized boolean isEmpty() {
    return talk == null;
  }

  private synchronized void addTalk(String talk) throws InterruptedException {
    logger.trace("Add talk start.");
    // while (true) {BGH
    while (!isEmpty()) {
      logger.debug("add talk... have to wait() ");
      wait();
    }
    Thread.sleep(200);
    this.talk = talk;
    logger.debug(" Add talk '" + talk + "'.");
    notifyAll();
    logger.trace("Add talk end.");
    return;  
    // }

  }

  private synchronized String removeTalk() throws InterruptedException {
    logger.trace("remove talk begin.");
    // while (true) {
    while (isEmpty()) {
      logger.debug("remove talk...have to wait()");
      wait();
    }
//    Thread.sleep(1500);
    String talk = this.talk;
    logger.debug("remove talk '" + talk + "'.");
    this.talk = null;
    notifyAll();
    logger.trace("remove talk end.");
    return talk;
    // }

  }

  public synchronized String getTalk() {
    return this.talk;
  }

  public void put(String talk) throws InterruptedException {
    addTalk(talk);
  }

  public String take() throws InterruptedException {
    return removeTalk();
  }

  public void interrupting() {
    //do nothing
  }
  
  

}
