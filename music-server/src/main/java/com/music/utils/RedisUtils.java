package com.music.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author lyj
 * @since 2022/7/8
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate redisTemplate;

    //写入缓存
    public void set(final String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    //写入缓存并设置过期时间
    public void set(final String key,String value,long time){
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    //通过Key取值
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    //通过key删除value
    public void del(String key){
        redisTemplate.delete(key);
    }

    //通过key批量删除
    public void del(List<String> keys){
        redisTemplate.delete(keys);
    }

    //通过key设置过期时间
    public boolean expire(String key, long time){
        return redisTemplate.expire(key,time, TimeUnit.SECONDS);
    }

    //通过key获取过期时间
    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    //通过key判断是否存在
    public boolean IsExist(String key){
        return redisTemplate.hasKey(key);
    }

    //自增
    public long incr(String key){
        return redisTemplate.opsForValue().increment(key);
    }

    //自减
    public long decr(String key){
        return redisTemplate.opsForValue().decrement(key);
    }

    //设置hash
    public void Hset(String key,String hashKey,Object value){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    //获取hash
    public Object Hget(String key,String hashKey){
        return redisTemplate.opsForHash().get(key,hashKey);
    }

    //设置hash并设置过期时间
    public boolean Hset(String key,String hashKey,Object value, long time){
        redisTemplate.opsForHash().put(key,hashKey,value);
        return expire(key,time);
    }

    //获取hash的所有数据
    public Object HgetAll(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    //设置整个hash，并设置过期时间
    public boolean Hsetall(String key, Map<String,Object> map,long time){
        redisTemplate.opsForHash().putAll(key,map);
        return expire(key,time);
    }

    //设置整个hash
    // ？通配符，不知道什么类型，一般用于 不允许 key/value 的限制（只读）
    public void HsetAll(String key, Map<String,?> map){
        redisTemplate.opsForHash().putAll(key,map);
    }

    //删除hash中的属性
    public void Hdel(String key,Object... keys){
        redisTemplate.opsForHash().delete(key,keys);
    }

    //判断hash中是否有该属性
    public boolean HIsexist(String key,String hashKey){
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }

    //hash中递增
    public long Hincr(String key,String hashKey,long data){
        return redisTemplate.opsForHash().increment(key,hashKey,data);
    }

    //hash中递减
    public long Hdecr(String key,String hashKey,long data){
        return redisTemplate.opsForHash().increment(key,hashKey,-data);
    }

    //获取set结构
    public Set<Object> Sget(String key){
        return redisTemplate.opsForSet().members(key);
    }

    //向set中添加属性
    public long Sset(String key,Object... value){
        return redisTemplate.opsForSet().add(key,value);
    }

    //向set中添加属性和过期时间
    public long Sset(String key, long time, Object... value){
        long count = redisTemplate.opsForSet().add(key,value);
        expire(key,time);
        return count;
    }

    //判断是否为set中的属性
    public boolean SIsexist(String key,Object keys){
        return redisTemplate.opsForSet().isMember(key,keys);
    }

    //获取set的长度
    public long Slength(String key){
        return redisTemplate.opsForSet().size(key);
    }

    //删除set中的属性
    public long Sremove(String key,Object... keys){
        return redisTemplate.opsForSet().remove(key,keys);
    }

    //获取list中的属性
    public List<Object> Lrange(String key, long start, long end){
        return redisTemplate.opsForList().range(key,start,end);
    }

    //获取list的长度
    public long Llenght(String key){
        return redisTemplate.opsForList().size(key);
    }

    //根据索引获取list的属性
    public Object LrangeByIndex(String key,long index){
        return redisTemplate.opsForList().index(key,index);
    }

    //向list填加属性
    public long Lpush(String key,Object value){
        return redisTemplate.opsForList().rightPush(key,value);
    }

    //向list中添加属性和过期时间
    public long Lpush(String key,Object value,long time){
        long count = redisTemplate.opsForList().rightPush(key,value);
        expire(key,time);
        return count;
    }

    //向list中批量添加属性
    public long LpullAll(String key,Object... values){
        return redisTemplate.opsForList().rightPushAll(key,values);
    }

    //向list中批量添加属性和过期时间
    public long LpullAll(String key,long time,Object... values){
        long count = redisTemplate.opsForList().rightPushAll(key,values);
        expire(key,time);
        return count;
    }

    //从list中移除属性
    public long Lremove(String key,long count,Object value){
        return redisTemplate.opsForList().remove(key,count,value);
    }
}
