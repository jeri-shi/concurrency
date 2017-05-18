/**
 * 
 */
package bow;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author shijin
 *
 */
public class Friend {
  private final static Logger logger = LogManager.getLogger(Friend.class);
  private String name;
  private final Lock lock = new ReentrantLock();

  public Friend(String name) {
    this.name = name;
  }

  public void bow(Friend bower) {
    if (implementBow(this, bower)) {
      logger.info(this + " bow to " + bower);
      bower.bowBack(this);
      bower.lock.unlock();
      this.lock.unlock();
    } else {
      logger.debug(this + " has to wait a monment.");
    }
  }

  public void bowBack(Friend bower) {
    logger.info(this + " bow back to " + bower);
  }

  public String toString() {
    return this.name;
  }

  private boolean implementBow (Friend bowee, Friend bower) {
    boolean boweeLock = false;
    boolean bowerLock = false;
    
    try {
      boweeLock = bowee.lock.tryLock();
      bowerLock = bower.lock.tryLock();
      logger.debug(bowee + "'s lock:" + boweeLock + ", " + bower + "'s lock:" + bowerLock);
    } finally {
      if (!(boweeLock && bowerLock)) {
        if (boweeLock) {
          logger.trace("release bowee:" + bowee + "'s lock.");
          bowee.lock.unlock();
        }
        if (bowerLock) {
          logger.trace("release bowwer:" + bower + "'s lock.");
          bower.lock.unlock();
        }
      }
    }
      return boweeLock && bowerLock;
    }
}
