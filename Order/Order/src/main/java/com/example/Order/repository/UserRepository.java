package com.example.Order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Oder.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
