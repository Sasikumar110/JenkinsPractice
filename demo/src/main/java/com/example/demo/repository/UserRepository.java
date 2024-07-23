package com.example.demo.repository;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LoginEntity;

@Repository
public interface UserRepository extends JpaRepository<LoginEntity,Integer>{

	Optional<LoginEntity> findByUserName(String username);
}
