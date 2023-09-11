package com.example.elkredis.Service;


import com.example.elkredis.DTO.Request.AddRoleRequestDTO;
import com.example.elkredis.DTO.Response.ResponseObjectDTO;
import com.example.elkredis.model.Users;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;


public interface UserService {
    List<Users> getAllUser();
    Optional<Users> getById(Long id);
    ResponseObjectDTO updateUser(Users newUser, Long id);
    ResponseObjectDTO deleteUser(Long id);
    UserDetails convertUserToUserDetail(Users users);
    public ResponseObjectDTO addRoleForUser(AddRoleRequestDTO addRoleRequestDTO);


}
