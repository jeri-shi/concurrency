package learn.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
  private final static Logger logger = LogManager.getLogger(Send.class);
  private final static String QUEUE_NAME = "hello";
  
  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String name = "Hello RabbitMQ!";
    
    channel.basicPublish("", QUEUE_NAME, null, name.getBytes());
    logger.info(" [x] Sent '"+name+"'");
    
    channel.close();
    connection.close();
  }
}
