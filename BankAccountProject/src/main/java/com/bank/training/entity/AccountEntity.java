package com.bank.training.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(value = Include.NON_NULL)
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id", nullable = false)
	private Long accountNumber;
	
	@Column(name = "account_type", nullable = false)
	private String accountType;
	
	@OneToOne
	private UserEntity user;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", nullable = false)
	private Date creationDate;
	
	@Column(name = "balance", nullable = false)
	private Long balance;

	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public UserEntity getUserId() {
		return user;
	}
	public void setUserId(UserEntity user) {
		this.user = user;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
