package com.example.elkredis.Config;

import com.example.elkredis.model.MessageDTO1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class Publisher3 {
    @Autowired
    private RedisTemplate<String, MessageDTO1> redisTemplate;
    @Qualifier("topicA")
    @Autowired
    private ChannelTopic topicA;

    @Qualifier("topicB")
    @Autowired
    private ChannelTopic topicB;


    public void publishAtoB(MessageDTO1 messageDTO) {

        redisTemplate.convertAndSend("Product-server", messageDTO);
        System.out.println("Sending message A to B: " );
    }


    public void publishBtoA(MessageDTO1 messageDTO) {

        redisTemplate.convertAndSend("Customer-server", messageDTO);
        System.out.println("Sending message B to A: ");
    }


    public void pushApiToRedisQueue( MessageDTO1 messageDTO1) {

        redisTemplate.opsForList().leftPush(messageDTO1.getMethod(),messageDTO1);
        System.out.println("Sending api to Rqueue: ");
    }


}
