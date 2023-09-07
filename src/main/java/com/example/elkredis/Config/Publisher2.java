//package com.example.elkredis.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Publisher2 implements MessagePublisher{
//    @Autowired
//    private RedisTemplate<String, MessageDTO1> redisTemplate;
//    @Qualifier("topicA")
//    @Autowired
//    private ChannelTopic topicA;
//
//    @Qualifier("topicB")
//    @Autowired
//    private ChannelTopic topicB;
//
////    public Publisher() {
////    }
////
////    public Publisher(
////            RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
////        this.redisTemplate = redisTemplate;
////        this.topic = topic;
////    }
//
//    @Override
//    public void publishAtoB(MessageDTO1 messageDTO) {
//
//        redisTemplate.convertAndSend("Product-server", messageDTO);
//        System.out.println("Sending message A to B: " );
//    }
//
//    @Override
//    public void pushApiToRedisQueue(MessageDTO1 messageDTO) {
//
//        redisTemplate.opsForList().leftPush(messageDTO.getMethod(), messageDTO);
//        System.out.println("Sending api to Rqueue: ");
//    }
//
//    @Override
//    public MessageDTO1 getForKey(String key) {
//        return  redisTemplate.opsForList().index(key,0);
//    }
//
//
//}
