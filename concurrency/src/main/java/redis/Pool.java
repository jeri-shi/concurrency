package redis;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Pool {

  private final static Logger logger = LogManager.getLogger(Pool.class);
  private final static JedisPoolConfig poolConfig = buildPoolConfig();
  private final static JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1");

  private static JedisPoolConfig buildPoolConfig() {
    logger.info("init pool");
    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(128);
    poolConfig.setMaxIdle(128);
    poolConfig.setMinIdle(16);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setTestWhileIdle(true);
    poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
    poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
    poolConfig.setNumTestsPerEvictionRun(3);
    poolConfig.setBlockWhenExhausted(true);
    return poolConfig;
  }
  
  public void process() {
    try(Jedis jedis = jedisPool.getResource()) {
      jedis.set("pool", jedis.toString());
      logger.info(jedis.get("pool"));
    }
    
    
  }
  
  public static void destroyPool() {
    jedisPool.close();
  }

  public static void main(String[] args) {
    Pool j1 = new Pool();
    Pool j2 = new Pool();
    j1.process();
    j2.process();
    
    Pool.destroyPool();
  }

}
