package com.bank.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.training.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>{

}
