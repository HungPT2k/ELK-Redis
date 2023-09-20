package com.example.elkredis.Service;


import com.example.elkredis.DTO.Request.AddRoleRequestDTO;
import com.example.elkredis.DTO.Request.UserUpdateAllDTO;
import com.example.elkredis.DTO.Request.UserUpdateDTO;
import com.example.elkredis.DTO.Response.ResponseObjectDTO;
import com.example.elkredis.model.Users;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;


public interface UserService {
    ResponseObjectDTO getAllUser();
    Optional<Users> getById(Long id);
    ResponseObjectDTO updateUser(UserUpdateDTO newUser, Long id);
    ResponseObjectDTO updateUserBySuper(UserUpdateAllDTO newUser, Long id);
    ResponseObjectDTO deleteUser(Long id);
    ResponseObjectDTO ActiveUser(Long id);
    UserDetails convertUserToUserDetail(Users users);
    public ResponseObjectDTO addRoleForUser(AddRoleRequestDTO addRoleRequestDTO);


}
