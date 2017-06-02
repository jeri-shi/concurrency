package redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class PubSub {
  private static final Logger logger = LogManager.getLogger(PubSub.class);

  public class Pub extends Thread {

    @Override
    public void run() {
      Jedis jPublisher = new Jedis();
      for (int i=0; i < 10; i++) {
      jPublisher.publish("channel", "Hello Redis" + i);
      }
      jPublisher.close();
    }

  }

  public class Sub extends Thread {
    @Override
    public void run() {
      Jedis jSubscriber = new Jedis();
      jSubscriber.subscribe(new JedisPubSub() {

        @Override
        public void onMessage(String channel, String message) {
          logger.info("Receive message:" + message + " from channel");
        }
        
      }, "channel");


      jSubscriber.close();

    }
 }

  public static void main(String[] args) {
    PubSub pb = new PubSub();
    Pub pub = pb.new Pub();
    Sub sub = pb.new Sub();

    pub.start();
    sub.start();

    try {
      pub.join();
      sub.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
