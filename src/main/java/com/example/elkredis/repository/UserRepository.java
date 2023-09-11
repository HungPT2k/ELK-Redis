package com.example.elkredis.repository;

import com.example.elkredis.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,Long> {
    List<Users> findByEmail(String email);
    List<Users> findUsersByNameUser(String name);

}
