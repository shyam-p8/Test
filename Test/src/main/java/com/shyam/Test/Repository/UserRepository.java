package com.shyam.Test.Repository;

import com.shyam.Test.Entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Boolean existsByEmail(String email);
    Boolean existsByName(String name);

    // get all user list directly without using Dto class
    List<User> findAll();
}
