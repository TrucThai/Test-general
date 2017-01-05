package com.biglabs.coap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lavalamp on 24/11/2016.
 */
public class JedisPoolWraper implements Serializable{
    private final Logger logger = LoggerFactory.getLogger(JedisPoolWraper.class);
    private JedisPool jedisPool = null;
    private KryoWraper mapper = null;
    private  String servers = null;
    private int port;

    public JedisPoolWraper(String  servers,int port){
        this.servers = servers;
        this.port = port;
    }

    public JedisPool getJedisPool(){
        initialize();
        return jedisPool;
    }

    private void initialize(){
        synchronized (this){
            if (jedisPool == null) {
                logger.info("### Create JedisPool client");

                JedisPoolConfig jedisPoolConfig =  new JedisPoolConfig();
                jedisPool = new JedisPool(jedisPoolConfig,this.servers,this.port);
                mapper = new KryoWraper(null);

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    jedisPool.destroy();
                    logger.info("###### CLOSED JedisPool client");
                }));
            }
        }
    }

    public void set(String key,Object object){
        try(Jedis jedis = getJedisPool().getResource()){
            byte[] value = objectToBytes(object);
            jedis.set(key.getBytes(),value);
        }catch (Exception ex){
            logger.error("JedisPoolWraper hget " + key ,ex);
        }
    }

    public <T> T get(String key,Class<T> type){
        try(Jedis jedis = getJedisPool().getResource()){
            byte[] value = jedis.get(key.getBytes());
            return bytesToObject(value,type);
        }catch (Exception ex){
            logger.error("JedisPoolWraper get " + key,ex);
            return null;
        }
    }

    public void hset(String key,String hashKey,Object object){
        try(Jedis jedis = getJedisPool().getResource()){
            byte[] value =  objectToBytes(object);
            jedis.hset(key.getBytes(),hashKey.getBytes(),value);
        }catch (Exception ex){
            logger.error("JedisPoolWraper hset " + key + " " + hashKey,ex);
        }
    }

    public <T> T hget(String key,String hashKey,Class<T> type){
        try(Jedis jedis = getJedisPool().getResource()){
            byte[] value = jedis.hget(key.getBytes(),hashKey.getBytes());
            return bytesToObject(value,type);
        }catch (Exception ex){
            logger.error("JedisPoolWraper hget " + key + " "+ hashKey,ex);
            return null;
        }
    }

    public void publish(String channel, String message){
        try(Jedis jedis = getJedisPool().getResource()){
            jedis.publish(channel,message);
        }catch (Exception ex){
            logger.error("JedisPoolWraper publish " + channel + " "+ message,ex);
        }
    }

    public void del(String key){
        try(Jedis jedis = getJedisPool().getResource()){
            jedis.del(key.getBytes());
        }catch (Exception ex){
            logger.error("JedisPoolWraper del " + key,ex);
        }
    }

    public void hdel(String key,String hashKey){
        try(Jedis jedis = getJedisPool().getResource()){
            jedis.hdel(key.getBytes(),hashKey.getBytes());
        }catch (Exception ex){
            logger.error("JedisPoolWraper hdel " + key + " " + hashKey,ex);
        }
    }

    public <T> Map<String, T>  hgetAll(String key,Class<T> type){
        try(Jedis jedis = getJedisPool().getResource()){
            Map<byte[],byte[]> values = jedis.hgetAll(key.getBytes());
            Map<String, T> ret = new HashMap<String, T>(values.size());
            for (Map.Entry<byte[], byte[]> entry : values.entrySet())
            {
                String hk  = new String(entry.getKey());
                T t =  bytesToObject(entry.getValue(),type);
                ret.put(hk,t);
            }
            return ret;
        }catch (Exception ex){
            logger.error("JedisPoolWraper hgetAll " + key ,ex);
            return null;
        }
    }

    public void zadd(String key,double score,String member){
        try(Jedis jedis = getJedisPool().getResource()){
            jedis.zadd(key.getBytes(),score,member.getBytes());
        }catch (Exception ex){
            logger.error("JedisPoolWraper zadd " + key + " " + member,ex);
        }
    }
    public long zcard(String key){
        try(Jedis jedis = getJedisPool().getResource()){
          return  jedis.zcard(key.getBytes());
        }catch (Exception ex){
            logger.error("JedisPoolWraper zcard " + key,ex);
            return -1;
        }
    }

    public Set<String> zrevrange(String key, long start, long end){
        try(Jedis jedis = getJedisPool().getResource()){
            return  jedis.zrevrange(key,start,end);
        }catch (Exception ex){
            logger.error("JedisPoolWraper zrevrange " + key,ex);
            return null;
        }
    }

    public void zrem(String key,String member){
        try(Jedis jedis = getJedisPool().getResource()){
            jedis.zrem(key.getBytes(),member.getBytes());
        }catch (Exception ex){
            logger.error("JedisPoolWraper zrem " + key + " " + member,ex);
        }
    }

    public  byte[] objectToBytes(Object oject){
        return mapper.serialize(oject);

    }

    public  <T> T bytesToObject (byte[] bytes, Class<T> type){
        return mapper.deserialize(bytes,type);

    }

}
