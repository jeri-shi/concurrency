package learn.rabbitmq;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


public class RPCClient {
  private static final Logger logger = LogManager.getLogger(RPCClient.class);

  private Connection connection;
  private Channel channel;
  private String requestQueueName = "rpc_queue";
  private String replyQueueName;

  public RPCClient() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    connection = factory.newConnection();
    channel = connection.createChannel();

    replyQueueName = channel.queueDeclare().getQueue();
  }

  public String call(String message) throws IOException, InterruptedException {
    String corrId = UUID.randomUUID().toString();

    AMQP.BasicProperties props =
        new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

    channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
    final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

    channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
          AMQP.BasicProperties properties, byte[] body) throws IOException {
        if (properties.getCorrelationId().equals(corrId)) {
          response.offer(new String(body, "UTF-8"));
        }
      }
    });

    return response.take();
  }

  public void close() throws IOException {
    connection.close();
  }

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException{
    RPCClient client = new RPCClient();
    
    logger.info(" [x] Requesting fib(20)");
    String response = client.call("20");
    logger.info(" [.] Got '"+response+"'");

    RPCClient client2 = new RPCClient();
    logger.info(" [x] Requesting fib(30)");
    response = client2.call("30");
    logger.info(" [.] Got '"+response+"'");
    
    client.close();
    client2.close();
  }

}
