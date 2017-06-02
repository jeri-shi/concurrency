package learn.rabbitmq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {
  private static final String EXCHANGE_NAME = "logs";
  private final static Logger logger = LogManager.getLogger(EmitLog.class);
//  private final static String QUEUE_NAME = "task_queue";

  public static void main(String[] args) throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    
//    boolean durable = true;
//    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
    String[] arg = null;
    try (BufferedReader read = new BufferedReader(new InputStreamReader(System.in))) {
      arg = read.readLine().split(" ");
    } ;

//    String message = getMessage(arg);

    for (String msg : arg) {
      channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
      logger.info(" [x] Sent '" + msg + "'");
    }

    channel.close();
    connection.close();
  }

  public static String getMessage(String[] args) {
    if (args.length < 1) {
      return "Hello World!";
    } else {
      return joinStrings(args, " ");
    }
  }

  private static String joinStrings(String[] strs, String delimiter) {
    int length = strs.length;
    if (length == 0)
      return "";
    StringBuilder words = new StringBuilder(strs[0]);

    for (int i = 1; i < length; i++) {
      words.append(delimiter).append(strs[i]);
    }

    return words.toString();
  }
}
