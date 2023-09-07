package com.example.elkredis.Config;

import com.example.elkredis.Service.IelkService;
import com.example.elkredis.model.MessageDTO1;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@EnableCaching
@CacheConfig

public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setDatabase(0);
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, MessageDTO1> redisTemplate() {
        RedisTemplate<String, MessageDTO1> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer(MessageDTO1.class));
        return template;
    }
    @Bean
    ChannelTopic topicA() {
        return new ChannelTopic("Product-server");
    }
    @Bean
    ChannelTopic topicB() {
        return new ChannelTopic("Customer-server");
    }
    @Bean
    RedisMessageListenerContainer container(MessageListenerAdapter messageListenerA) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.addMessageListener(messageListenerB(), topicB());
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerA, topicA());

        return container;
    }

//    @Bean
//    MessageListenerAdapter messageListenerB() {
//        return new MessageListenerAdapter(new receiver());
//    }
    @Bean
    MessageListenerAdapter messageListenerA(receiver3 receiver3) {
        return new MessageListenerAdapter(receiver3);
    }
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(cacheConfiguration)
                .build();
    }
}
