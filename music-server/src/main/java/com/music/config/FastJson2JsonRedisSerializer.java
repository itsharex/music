package com.music.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author lyj
 * @data 2022/6/25 17:04
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz){
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        // 空，直接返回空数组
        if(t == null) {
            return new byte[0];
        }
        try{
            // 使用 JSON 进行序列化成二进制数组，同时通过 SerializerFeature.WriteClassName 参数，声明写入类全名。
            return JSON.toJSONBytes(t, JSONWriter.Feature.WriteClassName);
        }catch (Exception e){
            throw new SerializationException("Could not serialize:" + e.getMessage(),e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        // 如果为空，则返回空对象
        if(bytes == null) {
            return null;
        }
        try {
            // 使用 JSON 解析成对象
            return (T)JSON.parseObject(new String(bytes,DEFAULT_CHARSET),clazz, JSONReader.Feature.SupportAutoType);
        }catch (Exception ex) {
            throw new SerializationException("Could not deserialize: " + ex.getMessage(), ex);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
