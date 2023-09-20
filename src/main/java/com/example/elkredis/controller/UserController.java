package com.example.elkredis.Controller;

import com.example.elkredis.Config.ReceiverUser;
import com.example.elkredis.DTO.Request.AddRoleRequestDTO;
import com.example.elkredis.DTO.Request.LoginRequestDTO;
import com.example.elkredis.DTO.Request.UserUpdateAllDTO;
import com.example.elkredis.DTO.Request.UserUpdateDTO;
import com.example.elkredis.DTO.Response.ResponseObjectDTO;
import com.example.elkredis.Service.AuthService;
import com.example.elkredis.Service.UserService;
import com.example.elkredis.model.Users;
import com.example.elkredis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/user")

public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserUpdateDTO.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ReceiverUser receiverUser;
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public String test(){
        return "Hello User" ;
    };
    @GetMapping(path = "/auth/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    ResponseObjectDTO getAllUser(){
        return receiverUser.findAllUser();
    }
//
//    @GetMapping("/deleteOK/{id}")
//    void deleteOk(@PathVariable(value = "id") Long id){
//
//        userRepository.deleteById(id);
//    }

    @PostMapping("/signin")
    public ResponseObjectDTO login(@RequestBody LoginRequestDTO userRequestDTO)  {
        log.info("Inside Login Function");
        String response = "Login at! " + new Date();
        log.info("Response => {}",response);
      return receiverUser.SignIn(userRequestDTO);
    }

    @PostMapping("/signup")
    public ResponseObjectDTO signup(@RequestBody Users user) {
     return   receiverUser.SignUp(user);
    }

    @GetMapping ("/auth/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseObjectDTO deleteUser(@PathVariable Long id){
        return receiverUser.deleteUser(id);
    }


    @PostMapping("/auth/addRole")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseObjectDTO addRole(@RequestBody AddRoleRequestDTO addRoleRequestDTO){
        return userService.addRoleForUser(addRoleRequestDTO);
    }
    @PostMapping("/auth/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')")
    public ResponseObjectDTO UpdateUser(@PathVariable(value = "id") Long id,@RequestBody UserUpdateDTO userUpdateDTO ){
        return  receiverUser.updateUser(id,userUpdateDTO);
    }

    @PostMapping("/auth/updateAll/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    public ResponseObjectDTO UpdateUserAll(@PathVariable(value = "id") Long id,@RequestBody UserUpdateAllDTO userUpdateDTO ){
        return  receiverUser.updateAllUser(id,userUpdateDTO);
    }
//    @GetMapping("/home")
//    public ResponseObjectDTO loginOauth2(HttpServletResponse httpServletResponse) {
//        System.out.println(httpServletResponse.getHeader("token"));
//     return new ResponseObjectDTO("200","Login oauth2 successfully",null);
//    }
}
