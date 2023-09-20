package com.example.elkredis.repository;

import com.example.elkredis.model.Users;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,Long> {
    List<Users> findByEmail(String email);
    @Cacheable(value = "loadUser",key = "#name",cacheManager = "cacheManager1")
    List<Users> findUsersByNameUser(String name);

    @Query(value = "SELECT * FROM User WHERE name_user like ?1",nativeQuery = true)
    List<Users> checkExitUser(String name);

    @Cacheable(value = "getAllUser", cacheManager = "cacheManager1")
    List<Users> findAll();



}
