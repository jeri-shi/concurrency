package learn.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


public class Worker {
  private static final Logger logger = LogManager.getLogger(Worker.class);
  private final static String QUEUE_NAME = "task_queue";

  public static void main(String[] args) throws TimeoutException, IOException {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    boolean durable = true;
    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
    channel.basicQos(1);
    logger.info(" [*] Waiting for messsages. To exit press CTRL+C. " + Thread.currentThread().getName());

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String customerTag, Envelope envelope,
          AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        logger.info(" [x] Recived '" + message + "'");
        
        try {
          doWork(message);
        } catch(InterruptedException e) {
          e.printStackTrace();
        } finally {
          logger.info(" [x] Done");
          channel.basicAck(envelope.getDeliveryTag(), false);
        }
        
        
      }
    };
    boolean autoAck = false;
    channel.basicConsume(QUEUE_NAME, autoAck, consumer);
  }
  
  private static void doWork(String task) throws InterruptedException {
    
    for(char ch: task.toCharArray()) {
      if (ch == '.') Thread.sleep(1000);
    }
  }

}
