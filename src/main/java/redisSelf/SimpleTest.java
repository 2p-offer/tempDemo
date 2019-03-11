package redisSelf;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.*;

/**
 * Created by 2P on 19-1-31.
 */
public class SimpleTest {
    public static void main(String[] args) {
        JedisPool jedisPool=new JedisPool("10.10.208.194",6379);
        Jedis jedis=jedisPool.getResource();
//
//        jedis.set("firstTest","testContent");
//        String firstTest = jedis.get("firstTest");
//        System.out.println("result :  "+firstTest);
//        jedis.set("firstTest","testContent222");
//        String firstTest2 = jedis.get("firstTest");
//        System.out.println("result :  "+firstTest2);
//        Long firstTest1 = jedis.del("firstTest");
//        System.out.println("delete success>>>"+firstTest1);
//=================================string=================
//=================================List=================
//        jedis.lpush("firstList","content1");
//        jedis.lpush("firstList","content2");
//        jedis.lpush("firstList","content3");
//        jedis.lpush("firstList","content4");
//
//        List<String> firstList = jedis.lrange("firstList", 0, 3);
//        for(String tmp :firstList){
//            System.out.println(tmp);
//        }
//=================================List=================
//=================================Keys=================
//         获取数据并输出
        Set<String> keys = jedis.keys("k*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()){
            String key = it.next();
            System.out.println(key);
        }
//=================================Keys=================
//=================================Hash=================
        jedis.hset("key1","field1","value1");
        jedis.hset("key1","field2","value2");
        Map<String, String> map = jedis.hgetAll("key1");
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();


        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey()+"=="+next.getValue());
        }


    }
}
