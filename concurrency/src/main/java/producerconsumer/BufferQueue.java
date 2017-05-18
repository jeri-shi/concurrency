package producerconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BufferQueue implements QueueInterface {

  private final static Logger logger = LogManager.getLogger(BufferQueue.class);
  private String[] buffer = new String[3];
  private int head, tail, count = 0;
  private final Lock lock = new ReentrantLock(true);
  private final Condition bufferFull = lock.newCondition();
  private final Condition bufferEmpty = lock.newCondition();

  public void put(String talk) throws InterruptedException {
    lock.lockInterruptibly();
    logger.trace("put method start..." + Thread.currentThread().getName());
    logger.debug("Get the lock " + Thread.currentThread().getName() + " " + lock);
    // if buffer is full, then wait
    try {
      while (count == buffer.length) {
        logger.debug("buffer is full in " + Thread.currentThread().getName());
        bufferFull.await();
        logger.debug(Thread.currentThread().getName() + " : isInterruppted="
            + Thread.currentThread().isInterrupted());
      }

      // if buffer is not full, then put talk in.
      buffer[tail] = talk;
      logger.debug("put '" + talk + "' in buffer[" + tail + "]");
      if (++tail == buffer.length)
        tail = 0;
      count++;
      printQueue();
      bufferEmpty.signal();
    } catch (InterruptedException e) {
      logger.info(Thread.currentThread().getName() + " was interrupped!");
      throw e;
    } finally {
      lock.unlock();
      logger.trace("put method end.");
    }

  }

  public String take() throws InterruptedException {
    lock.lockInterruptibly();
    String talk = null;
    logger.trace("take method start..." + Thread.currentThread().getName());
    logger.debug("Get the lock " + Thread.currentThread().getName() + " " + lock);
    try {
      while (count == 0) {
        logger.debug("buffer is empty in " + Thread.currentThread().getName());
        bufferEmpty.await();
        logger.debug(Thread.currentThread().isInterrupted());
      }
      
      talk = buffer[head];
      logger.debug("take '" + buffer[head] + "' out of buffer[" + head + "]");
      if (++head == buffer.length)
        head = 0;
      count--;
      printQueue();
      bufferFull.signal();
    } catch (InterruptedException e) {
      logger.info(Thread.currentThread().getName() + " was interrupped!");
      throw e;
    } finally {
      lock.unlock();
      logger.trace("take method end.");
    }
    return talk;

  }

  private String printQueue() {
    StringBuffer out = new StringBuffer();
    for(int i=0; i<buffer.length; i++) {
      out.append("[");
      out.append(buffer[i]);
      out.append("]");
    }
    logger.debug(out);
    return out.toString();
  }

  public void interrupting() {
    
  }
  
  @Override
  public String toString() {
    return printQueue();
  }

}
