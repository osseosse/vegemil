package com.vegemil.util;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.time.Duration;


@Service
public class RedisUtil {
	
	@Autowired
    RedisTemplate<String, String> redisTemplate;

    public String getData(String key){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    /**
     * 일 단위 유지 기간 설정
     * @param key
     * @param value
     * @param days
     */
    public void setDataExpire(String key,String value,long days){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofDays(days);
        valueOperations.set(key,value,expireDuration);
    }
    
    
    /**
     * 시간 단위 유지 기간 설정
     * @param key
     * @param value
     * @param hours
     */
    public void setHourExpire(String key,String value,long hours){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofHours(hours);
        valueOperations.set(key,value,expireDuration);
    }
    
    
    
    public void deleteData(String key){
    	redisTemplate.delete(key);
    }
    
}