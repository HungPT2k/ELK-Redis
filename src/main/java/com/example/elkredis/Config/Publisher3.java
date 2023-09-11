package com.example.elkredis.Config;

import com.example.elkredis.DTO.Request.MessageDTO1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
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


    public void publishToA(MessageDTO1 messageDTO) {

        redisTemplate.convertAndSend(topicA.getTopic(), messageDTO);
        System.out.println("Sending request  to A: " );
    }
    public void publishToB(MessageDTO1 messageDTO) {

        redisTemplate.convertAndSend(topicB.getTopic(), messageDTO);
        System.out.println("Sending request  to B: " );
    }


    public void addGuiRedis( MessageDTO1 messageDTO1) {

        redisTemplate.opsForList().leftPush(messageDTO1.getMethod(),messageDTO1);
        System.out.println("add request to redis gui: ");
    }


}
