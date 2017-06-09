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


public class ReceiveLogsTopic {
  private static final String EXCHANGE_NAME = "topic_logs";
  private static final Logger logger = LogManager.getLogger(ReceiveLogsTopic.class);

  public static void main(String[] args) throws TimeoutException, IOException {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, "topic");

    if (args.length < 1) {
      logger.error("Usage: ReceiveLogsDirect [binding_key]");
      System.exit(1);
    }

    String queueName = channel.queueDeclare().getQueue();
    for (String bindingKey : args) {
      channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
    }
    logger.info(" [*] Waiting for messsages. To exit press CTRL+C. ");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String customerTag, Envelope envelope,
          AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        logger.info(" [x] Recived '" + envelope.getRoutingKey() + "':'" + message + "'");

      }
    };

    channel.basicConsume(queueName, true, consumer);
  }


}
