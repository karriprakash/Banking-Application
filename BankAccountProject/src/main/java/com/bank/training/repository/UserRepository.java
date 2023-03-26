package com.bank.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.training.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

}
