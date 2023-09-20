package com.example.elkredis.security;

import com.example.elkredis.model.Users;
import com.example.elkredis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {


 private final UserRepository userRepository;
//

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final List<Users> Users = userRepository.findUsersByNameUser(username);

    if (Users.isEmpty()) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    return UserPrincipal.create(Users.get(0));
  }




}
