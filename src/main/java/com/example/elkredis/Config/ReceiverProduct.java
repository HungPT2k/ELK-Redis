package com.example.elkredis.Config;


import com.example.elkredis.DTO.Request.MessageDTO1;
import com.example.elkredis.DTO.Response.MessageDTO;
import com.example.elkredis.DTO.Response.ResponseCommon;
import com.example.elkredis.Service.IelkService;

import com.example.elkredis.constant.CommonConstant;
import com.example.elkredis.model.Product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;


@Service

public class ReceiverProduct implements MessageListener { // mỗi class subscriber một chanel


    private final Publisher3 publisher;
    private final IelkService elkService;
    public static boolean check = false;
    ObjectMapper mapper = new ObjectMapper();

    public ReceiverProduct(Publisher3 publisher, IelkService elkService) {
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
            publisher.addGuiRedis(new MessageDTO1("", m.getMethod(), myObj.toString()));
            switch (Objects.requireNonNull(m).getMethod()) {
                case CommonConstant.MethodProduct.FINDBYID:
                    System.out.println("findByid .............ok");
                    check = true;
                    break;

                case CommonConstant.MethodProduct.UPDATEPRODUCT:

                    System.out.println("update .............ok");
                    check = true;
                    break;

                case CommonConstant.MethodProduct.FINDALL:
                    System.out.println("findAll .............ok");
                    check = true;
                    break;
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
        check = false;
        ResponseCommon res;
        MessageDTO1 messageDTO = new MessageDTO1();
        messageDTO.setMethod(CommonConstant.MethodProduct.FINDBYID);
        messageDTO.setMessDetail("get product by id");
        System.out.println(elkService.findById(id).get().getName() + "-------------");
        publisher.publishToA(messageDTO);
        long startTime = System.currentTimeMillis(); //fetch starting time
        while (!check && (System.currentTimeMillis() - startTime) < 2000) {
            res = null;
        }
        if (check) {
            res = new ResponseCommon("200", "Get product by id " + id, elkService.findById(id));
        } else {
            res = new ResponseCommon("200", "queue null", null);
        }
        return res;
    }


    public ResponseCommon updateProduct(Long id, Product product) throws InterruptedException {
        check = false;
        ResponseCommon res;
        MessageDTO1 messageDTO = new MessageDTO1();
        messageDTO.setMethod(CommonConstant.MethodProduct.UPDATEPRODUCT);
        messageDTO.setMessDetail("Update product by id");
        publisher.publishToA(messageDTO);
        long startTime = System.currentTimeMillis(); //fetch starting time
        while (!check && (System.currentTimeMillis() - startTime) < 2000) {
            res = null;
        }
        if (check) {
            res = new ResponseCommon("200", "Update  product by id"+ id, elkService.UpdateProduct(id,product));
        } else {
            res = new ResponseCommon("200", "queue null", null);
        }
        return res;
    }


    public ResponseCommon findAllProduct() {
        check = false;
        ResponseCommon res;
        MessageDTO1 messageDTO1 = new MessageDTO1();
        messageDTO1.setMethod(CommonConstant.MethodProduct.FINDALL);
        messageDTO1.setMessDetail("Find all product");
        System.out.println(elkService.findAll().get(0).getName() + "____________");
        publisher.publishToA(messageDTO1);
        long startTime = System.currentTimeMillis(); //fetch starting time
        while (!check && (System.currentTimeMillis() - startTime) < 2000) {
            res = null;
        }
        if (check) {
            res = new ResponseCommon("200", "Find all product ", elkService.findAll());
        } else {
            res = new ResponseCommon("200", "queue null", null);
        }
        return res;
    }
}
