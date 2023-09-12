package com.example.elkredis.Config;

import com.example.elkredis.DTO.Request.LoginRequestDTO;
import com.example.elkredis.DTO.Request.MessageDTO1;
import com.example.elkredis.DTO.Request.UserUpdateDTO;
import com.example.elkredis.DTO.Response.MessageDTO;
import com.example.elkredis.DTO.Response.ResponseCommon;
import com.example.elkredis.DTO.Response.ResponseObjectDTO;
import com.example.elkredis.Service.AuthService;

import com.example.elkredis.Service.UserService;
import com.example.elkredis.constant.CommonConstant;
import com.example.elkredis.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
@Service
public class ReceiverUser implements MessageListener { // mỗi class subscriber một chanel


    private final Publisher3 publisher;
    private final AuthService authService;
    private final UserService userService;
    public static boolean check = false;
    ObjectMapper mapper = new ObjectMapper();

    public ReceiverUser(Publisher3 publisher, AuthService authService, UserService userService) {
        this.publisher = publisher;
        this.authService = authService;
        this.userService = userService;
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

                case CommonConstant.MethodUser.SIGNUP:
                    System.out.println("SigUp .............ok");
                    check = true;
                    break;
                case CommonConstant.MethodUser.SIGIN:
                    System.out.println("SigIn .............ok");
                    check = true;
                    break;
                case CommonConstant.MethodUser.GETBYID:
                    System.out.println("findByid user.............ok");
                    check = true;
                    break;

                case CommonConstant.MethodUser.UPDATEUSER:

                    System.out.println("update user.............ok");
                    check = true;
                    break;

                case CommonConstant.MethodUser.FINDALLUSER:
                    System.out.println("findAll user.............ok");
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
    public ResponseObjectDTO SignUp(Users users) throws InterruptedException {
        check = false;
        ResponseObjectDTO res;
        MessageDTO1 messageDTO = new MessageDTO1();
        messageDTO.setMethod(CommonConstant.MethodUser.GETBYID);
        messageDTO.setMessDetail("get user by id");
        publisher.publishToA(messageDTO);
        long startTime = System.currentTimeMillis(); //fetch starting time
        while (!check && (System.currentTimeMillis() - startTime) < 2000) {
            res = null;
        }
        if (check) {
            res =  authService.signUp(users);
        } else {
            res = new ResponseObjectDTO("200", "queue null", null);
        }
        return res;
    }


    public ResponseObjectDTO SignIn(LoginRequestDTO loginRequestDTO) throws InterruptedException {
        check = false;
        ResponseObjectDTO res;
        MessageDTO1 messageDTO = new MessageDTO1();
        messageDTO.setMethod(CommonConstant.MethodUser.SIGIN);
        messageDTO.setMessDetail("Login user ..");
        publisher.publishToA(messageDTO);
        long startTime = System.currentTimeMillis(); //fetch starting time
        while (!check && (System.currentTimeMillis() - startTime) < 2000) {
            res = null;
        }
        if (check) {
            res = authService.signIn(loginRequestDTO);
        } else {
            res = new ResponseObjectDTO("200", "queue null", null);
        }
        return res;
    }


    public ResponseObjectDTO findAllUser() {
        check = false;
        ResponseObjectDTO res;
        MessageDTO1 messageDTO1 = new MessageDTO1();
        messageDTO1.setMethod(CommonConstant.MethodUser.FINDALLUSER);
        messageDTO1.setMessDetail("Find all user");
        publisher.publishToA(messageDTO1);
        long startTime = System.currentTimeMillis(); //fetch starting time
        while (!check && (System.currentTimeMillis() - startTime) < 2000) {
            res = null;
        }
        if (check) {
            res = userService.getAllUser();
        } else {
            res = new ResponseObjectDTO("200", "queue null", null);
        }
        return res;
    }
    public ResponseObjectDTO updateUser(Long id , UserUpdateDTO userUpdateDTO) {
        check = false;
        ResponseObjectDTO res;
        MessageDTO1 messageDTO1 = new MessageDTO1();
        messageDTO1.setMethod(CommonConstant.MethodUser.UPDATEUSER);
        messageDTO1.setMessDetail("Update user");
        publisher.publishToA(messageDTO1);
        long startTime = System.currentTimeMillis(); //fetch starting time
        while (!check && (System.currentTimeMillis() - startTime) < 2000) {
            res = null;
        }
        if (check) {
            res = userService.updateUser(userUpdateDTO,id);
        } else {
            res = new ResponseObjectDTO("200", "queue null", null);
        }
        return res;
    }
}
