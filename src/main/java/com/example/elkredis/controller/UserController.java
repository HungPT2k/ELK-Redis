package com.example.elkredis.controller;

import com.example.elkredis.DTO.Request.AddRoleRequestDTO;
import com.example.elkredis.DTO.Request.LoginRequestDTO;
import com.example.elkredis.DTO.Response.ResponseObjectDTO;
import com.example.elkredis.Service.AuthService;
import com.example.elkredis.Service.UserService;
import com.example.elkredis.model.Users;
import com.example.elkredis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public String test(){
        return "Hello User" ;
    };
    @GetMapping(path = "/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    List<Users> getAllUser(){
        return userService.getAllUser();
    }
    @GetMapping("/deleteOK/{id}")
    void deleteOk(@PathVariable(value = "id") Long id){

        userRepository.deleteById(id);
    }

    @PostMapping("/signin")
    public ResponseObjectDTO login(@RequestBody LoginRequestDTO userRequestDTO) {
      return authService.signIn(userRequestDTO);
    }

    @PostMapping("/signup")
    public ResponseObjectDTO signup(@RequestBody Users user) {
     return   authService.signUp(user);
    }

    @GetMapping ("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseObjectDTO deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
    @PostMapping("/addRole")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseObjectDTO addRole(@RequestBody AddRoleRequestDTO addRoleRequestDTO){
        return userService.addRoleForUser(addRoleRequestDTO);
    }

    @GetMapping("/home")
    public ResponseObjectDTO loginOauth2(HttpServletResponse httpServletResponse) {
        System.out.println(httpServletResponse.getHeader("token"));
     return new ResponseObjectDTO("200","Login oauth2 successfully",null);
    }
}
