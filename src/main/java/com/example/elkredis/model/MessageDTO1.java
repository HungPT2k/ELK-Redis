package com.example.elkredis.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash
public class MessageDTO1 implements Serializable {
    private String messDetail;
    private String method;
    private String time;
//    private String nameServer;
//    private HashMap<String,String> parameters;
//    private Object object;
}
