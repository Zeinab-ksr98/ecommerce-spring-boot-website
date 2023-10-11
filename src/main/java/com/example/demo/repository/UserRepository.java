package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "select * from users where username = ?1", nativeQuery = true)
    Optional<User> findUserByUserName(String username);

    boolean existsByUsername(String username);
    @Query(value = "select * from users where email = ?1", nativeQuery = true)
    Optional<User> findUserByEmail(String email);
    @Query(value = "select * from users where email = ?1 and username = ?2", nativeQuery = true)
    Optional<User> findUserByEmailAndName(String email,String username);

    boolean existsByEmail(String email);

}
