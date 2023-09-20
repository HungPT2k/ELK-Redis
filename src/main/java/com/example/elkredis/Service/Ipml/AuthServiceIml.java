package com.example.elkredis.Service.Ipml;


import com.example.elkredis.DTO.Request.LoginRequestDTO;
import com.example.elkredis.DTO.Response.ResponseObjectDTO;
import com.example.elkredis.DTO.Response.UserResponseDTO;
import com.example.elkredis.Service.AuthService;
import com.example.elkredis.model.Roles;
import com.example.elkredis.model.Users;
import com.example.elkredis.repository.UserRepository;
import com.example.elkredis.security.JwtTokenProvider;
import com.example.elkredis.security.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class AuthServiceIml implements AuthService {
    private JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetails myUserDetails;

    @Override
    public ResponseObjectDTO signIn(LoginRequestDTO userRequest) {
        try {
            String username = userRequest.getUserName();
            String password = userRequest.getPassWordUser();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = myUserDetails.loadUserByUsername(username);
            String token = jwtTokenProvider.CreateToken(userDetails);
            UserResponseDTO user = new UserResponseDTO();
            List<Users> users = userRepository.findUsersByNameUser(username);
            if (users.isEmpty()) return new ResponseObjectDTO("404", "User not found ", null);
            user.setUserName(users.get(0).getNameUser());
            user.setEmail(users.get(0).getEmail());
            user.setRole(users.get(0).getRole());
            user.setFullName(users.get(0).getFullName());
            user.setToken(token);
            user.setId(users.get(0).getId());
            return new ResponseObjectDTO("200", "Signin successfully ", user);
        } catch (AuthenticationException e) {
            return new ResponseObjectDTO("500", "Signin fail, name or pass is wrong ", null);

        }
    }

    @Override
    public ResponseObjectDTO signUp(Users appUser) {
        String name = appUser.getNameUser();
        String pass = appUser.getPassWordUser();
        if (checkExitsUser(name, pass)) {
            appUser.setPassWordUser(passwordEncoder.encode(pass));
            appUser.setRole(Roles.ROLE_CLIENT.toString());
            appUser.setIsDelete(false);
            userRepository.save(appUser);
            UserResponseDTO user = new UserResponseDTO();
            user.setUserName(appUser.getNameUser());
            user.setEmail(appUser.getEmail());
            user.setRole(appUser.getRole());
            user.setToken("");
            user.setId(appUser.getId());
            user.setFullName(appUser.getFullName());
            return new ResponseObjectDTO("200", "SignUp successfully ", user);
        } else {
            return new ResponseObjectDTO("500", "SignUp fail , userName or Pass is exit ", null);
        }
    }



    @Override
    public boolean checkExitsUser(String userName, String email) {
        return userRepository.findByEmail(email).size()==0 && userRepository.checkExitUser(userName).size()==0;
    }

}
