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
import com.rabbitmq.client.RpcServer;

public class RPCServer {
  private static final Logger logger = LogManager.getLogger(RpcServer.class);
  private static final String RPC_QUEUE_NAME = "rpc_queue";

  public static void main(String[] args) throws IOException, TimeoutException{
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    
    Connection connection = null;
    try{
      connection = factory.newConnection();
      Channel channel = connection.createChannel();
      
      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
      channel.basicQos(1);
      
      logger.info(" [x] Awaiting RPC requests");
      
      Consumer consumer = new DefaultConsumer(channel) {
        
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
          
          AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();
          String response = "";
          
          try{
            String message = new String(body, "UTF-8");
            int n = Integer.parseInt(message);
            
            logger.info(" [.] fib("+message+")");
            response += fib(n);
            Thread.sleep(10000);
            
          }catch(RuntimeException | InterruptedException e) {
            logger.error(" [.] " + e.getMessage());
          }finally {
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
            channel.basicAck(envelope.getDeliveryTag(), false);
          }
          
        }
      };
      
      channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
    } finally {

    }

  }

  private static int fib(int n) {
    if (n==0) return 0;
    if (n==1) return 1;
    return fib(n-1) + fib(n-2);
  }
}
