package com.example.elkredis.Service.Ipml;


import com.example.elkredis.DTO.Request.AddRoleRequestDTO;
import com.example.elkredis.DTO.Response.ResponseObjectDTO;
import com.example.elkredis.Service.UserService;
import com.example.elkredis.model.Roles;
import com.example.elkredis.model.Users;
import com.example.elkredis.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;



    @Override
    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public ResponseObjectDTO updateUser(Users newUser, Long id) {
        return null;
    }

    @Override
    public ResponseObjectDTO deleteUser(Long id) {
        Optional<Users> users = userRepository.findById(id);
        if (users.isEmpty()) return new ResponseObjectDTO("404", "Not found", null);
//       if(users.get().getRole().indexOf(Roles.ROLE_SUPERADMIN.toString())>0) return new ResponseObject("403","Not claim",null);
        if (users.get().getIsDelete()) {
            users.get().setIsDelete(false);
            userRepository.save(users.get());
            return new ResponseObjectDTO("200", "InActive user by id " + id, "");
        }
        users.get().setIsDelete(true);
        userRepository.save(users.get());
        return new ResponseObjectDTO("200", "Active user by id " + id, "");
    }






    @Override
    public UserDetails convertUserToUserDetail(Users users) {
        if (users == null) {
            return null;
        }
        String[] roles = users.getRole().split("\\|");

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roles[i]));
        }
        return new User(users.getNameUser(), users.getPassWordUser(), grantedAuthorities);
    }

    @Override
    public ResponseObjectDTO addRoleForUser(AddRoleRequestDTO addRoleRequestDTO) {
        System.out.println(addRoleRequestDTO);
        String id = addRoleRequestDTO.getIdUser();
        String IdRole = addRoleRequestDTO.getIdRole();
        String role = null;
        switch (IdRole) {
            case "1" -> role = Roles.ROLE_CLIENT.toString();
            case "2" -> role = Roles.ROLE_ADMIN.toString();
            case "3" -> role = Roles.ROLE_SUPERADMIN.toString();
            default -> {
            }
        }

        Optional<Users> user = userRepository.findById(Long.valueOf(id));
        if (user.isEmpty() || role == null) return new ResponseObjectDTO("404", "User is not already ", "");
        String userRole = user.get().getRole();
        if (!userRole.contains(role)) {
            userRole = userRole + "|" + role;
            user.get().setRole(userRole);
            userRepository.save(user.get());
            return new ResponseObjectDTO("200", "User id " + id + " add role " + role + " successfully", user.get());
        }
        return new ResponseObjectDTO("400", "false " + role + " is already", user.get());

    }

}
