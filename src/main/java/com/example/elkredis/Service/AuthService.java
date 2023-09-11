package com.example.elkredis.Service;

import com.example.elkredis.DTO.Request.LoginRequestDTO;
import com.example.elkredis.DTO.Response.ResponseObjectDTO;
import com.example.elkredis.model.Users;

public interface AuthService {
    ResponseObjectDTO signIn(LoginRequestDTO loginRequest);
    ResponseObjectDTO signUp(Users users);
    boolean checkExitsUser(String userName , String email);

}
