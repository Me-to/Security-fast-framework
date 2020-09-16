package com.example.qian.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis
 */
@Component
public class RememberMeHandler implements PersistentTokenRepository {
    /**
     * token定义的保存时长为15天
     */
    private static final Long TOKEN_VALID_DAYS = 15L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String key = generateTokenKey(token.getSeries());
        Map<String,String> map = new HashMap<>(8);
        map.put("username", token.getUsername());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", String.valueOf(token.getDate().getTime()));
        stringRedisTemplate.opsForHash().putAll(key, map);
        stringRedisTemplate.expire(key,TOKEN_VALID_DAYS, TimeUnit.DAYS);

        saveUsernameAndSeries(token.getUsername(), token.getSeries());
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String key = generateTokenKey(series);
        Boolean hasSeries = stringRedisTemplate.hasKey(key);
        if(hasSeries==null || !hasSeries){
            return;
        }
        Map<String,String> map = new HashMap<>(4);
        map.put("tokenValue", tokenValue);
        map.put("date", String.valueOf(lastUsed.getTime()));
        stringRedisTemplate.opsForHash().putAll(key, map);
        stringRedisTemplate.expire(key, TOKEN_VALID_DAYS, TimeUnit.DAYS);

        String username = stringRedisTemplate.opsForValue().get(generateUsernameAndSeriesKey(series));
        saveUsernameAndSeries(username, series);
    }

    @Override
    public void removeUserTokens(String username) {
        String series = stringRedisTemplate.opsForValue().get(generateUsernameAndSeriesKey(username));
        if (series==null||series.trim().length()<=0){
            return;
        }
        stringRedisTemplate.delete(generateTokenKey(series));
        stringRedisTemplate.delete(generateUsernameAndSeriesKey(username));
        stringRedisTemplate.delete(generateUsernameAndSeriesKey(series));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String key = generateTokenKey(seriesId);
        Map hashKeyValues = stringRedisTemplate.opsForHash().entries(key);
        if (hashKeyValues == null) {
            return null;
        }
        Object username = hashKeyValues.get("username");
        Object tokenValue = hashKeyValues.get("tokenValue");
        Object date = hashKeyValues.get("date");
        if (null == username || null == tokenValue || null == date) {
            return null;
        }
        long timestamp = Long.valueOf(String.valueOf(date));
        Date time = new Date(timestamp);

        return new PersistentRememberMeToken(String.valueOf(username), seriesId, String.valueOf(tokenValue), time);
    }



    /**
     * 互相保存，便于查询
     */
    private void saveUsernameAndSeries(String username, String series){
        stringRedisTemplate.opsForValue().set(generateUsernameAndSeriesKey(username),series,TOKEN_VALID_DAYS*2, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(generateUsernameAndSeriesKey(series),username,TOKEN_VALID_DAYS*2,TimeUnit.DAYS);
    }

    /**
     * 生成token key
     */
    private String generateTokenKey(String series) {
        return "spring:security:rememberMe:token:" + series;
    }

    /**
     * 生成key
     */
    private String generateUsernameAndSeriesKey(String usernameOrSeries) {
        return "spring:security:rememberMe:"+usernameOrSeries;
    }
}