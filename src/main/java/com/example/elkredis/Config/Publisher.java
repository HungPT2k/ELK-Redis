//package com.example.elkredis.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Publisher implements MessagePublisher{
//    @Autowired
//    private RedisTemplate<String, MessageDTO> redisTemplate;
//
//    @Override
//    public void pushApiToRedisQueue(MessageDTO messageDTO) {
//
//        redisTemplate.opsForList().leftPush(messageDTO.getMethod(), messageDTO);
//        System.out.println("Sending api to Rqueue: ");
//    }
//
//    @Override
//    public MessageDTO getForKey(String key) {
//        return  redisTemplate.opsForList().index(key,0);
//    }
//
//    @Override
//    public MessageDTO getForKeyAndRemove(String key) {
//        return  redisTemplate.opsForList().leftPop(key);
//    }
//
//    @Override
//    public void Remove(String key, Long index) {
//
//    }
//
//
//}
