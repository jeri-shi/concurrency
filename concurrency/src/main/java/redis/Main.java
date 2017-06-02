package redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.set("events/city/rome", "32, 15, 223, 828");
    logger.info(jedis.get("events/city/rome"));
    logger.info(jedis.del("events/city/rome"));
    
    jedis.lpush("queue#tasks", "firstTask");
    jedis.lpush("queue#tasks", "secondTask");
    
    logger.info(jedis.lrange("queue#tasks", 0, -1));
    
    jedis.sadd("nicknames", "name#1");
    jedis.sadd("nicknames", "name#2");
    jedis.sadd("nicknames", "name#3");
    
    logger.info(jedis.smembers("nicknames"));
    logger.info(jedis.sismember("nicknames", "name#3"));
    
    jedis.hset("user#1", "name", "peter");
    jedis.hset("user#1", "job", "politician");
    
    logger.info(jedis.hget("user#1", "name"));
    logger.info(jedis.hgetAll("user#1"));
    
    Map<String, Double> scores = new HashMap<>();
    scores.put("PlayerOne", 3000.0);
    scores.put("PlayerTwo", 1500.0);
    scores.put("PlayerThree", 8200.0);
    
    scores.keySet().forEach(player -> {
        jedis.zadd("ranking", scores.get(player), player);
    });
    
    logger.info(jedis.zrevrange("ranking", 0, 1).iterator().next());
    logger.info(jedis.zrevrank("ranking", "PlayerTwo"));
    
    Transaction t = jedis.multi();
    t.sadd("friends#523", "523");
    t.sadd("friends#447", "447");
    t.exec();
    
    logger.info(jedis.smembers("friends#523"));
    
    String userOneId = "4352523";
    String userTwoId = "4849888";
    
    Pipeline p = jedis.pipelined();
    p.sadd("searched#" + userOneId, "paris");
    p.zadd("ranking", 126, userOneId);
    p.zadd("ranking", 325, userTwoId);
    Response<Boolean> pipeExists = p.sismember("searched#" + userOneId, "paris");
    Response<Set<String>> pipeRanking = p.zrange("ranking", 0, -1);
    p.sync();
    
    logger.info(pipeExists.get());
    logger.info(pipeRanking.get());
    
    jedis.close();
  }

}
