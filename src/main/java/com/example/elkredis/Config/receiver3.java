package com.example.elkredis.Config;


import com.example.elkredis.Service.IelkService;
import com.example.elkredis.constant.CommonConstant;
import com.example.elkredis.model.MessageDTO;
import com.example.elkredis.model.MessageDTO1;
import com.example.elkredis.model.Product;
import com.example.elkredis.model.ResponseCommon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.store.Lock;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;


@Service

public class receiver3 implements MessageListener { // mỗi class subscriber một chanel


    private final Publisher3 publisher;
    private final IelkService elkService;
    public static boolean check = false;
    final Object lock = new Object();
    ObjectMapper mapper = new ObjectMapper();


    public receiver3(Publisher3 publisher, IelkService elkService) {
        this.publisher = publisher;
        this.elkService = elkService;
    }

    // Nhận message của chanel đã subscriber
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            MessageDTO m = mapper.readValue(message.getBody(), MessageDTO.class);
            LocalDateTime myObj = LocalDateTime.now();
            // quan sát request nhận đc trên redis gui
            publisher.pushApiToRedisQueue(new MessageDTO1("",m.getMethod(),myObj.toString()));
            switch (Objects.requireNonNull(m).getMethod()) {
                case CommonConstant.Method.FINDBYID:
                    synchronized (lock) {
                        System.out.println("findByid .............ok");
                        check = true;
                        lock.notify();
                        break;
                    }
                case CommonConstant.Method.UPDATEPRODUCT:
                    synchronized (lock) {
                        System.out.println("update .............ok");
                        check = true;
                        lock.notify();
                        break;
                    }

                default:
                    System.out.println("Name method not find..........");
                    //    res= new ResponseCommon("404", "not found method", null);
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // method sẽ gọi đến redis queue trước khi dc thực thi
    public ResponseCommon findById(Long id) throws InterruptedException {
        MessageDTO1 messageDTO = new MessageDTO1();
        messageDTO.setMethod("findById");
        messageDTO.setMessDetail("get product by id");
        System.out.println(check);
        synchronized (lock) {
            publisher.publishAtoB(messageDTO);
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (check) {
                check = false;
                return new ResponseCommon("200", "Get product by id " + id, elkService.findById(id));
            }
            return null;
        }
    }




    public ResponseCommon updateProduct(Long id, Product product) throws InterruptedException {
        MessageDTO1 messageDTO = new MessageDTO1();
        messageDTO.setMethod("updateProduct");
        messageDTO.setMessDetail("Update product by id");
        synchronized (lock) {
            publisher.publishAtoB(messageDTO);
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (check) {
                check = false;
                return new ResponseCommon("200", "Update product by id "+id, elkService.createProduct(id,product));
            }
            return null;
        }
    }
}
