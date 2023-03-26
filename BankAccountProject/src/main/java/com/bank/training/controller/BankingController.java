package com.bank.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.training.entity.AccountDTO;
import com.bank.training.entity.AccountEntity;
import com.bank.training.entity.UserDTO;
import com.bank.training.entity.UserEntity;
import com.bank.training.model.GenericModel;
import com.bank.training.response.GenricResponse;
import com.bank.training.service.BankingService;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController("/accounts")
public class BankingController {

	@Autowired
	private BankingService bankingService;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<GenricResponse<UserEntity>> getUser(@PathVariable("userId") Long userId) {
		var genericResponse = new GenricResponse<UserEntity>();
		genericResponse.setResponse(bankingService.getUser(userId));
		genericResponse.setStatus(HttpStatus.OK.toString());
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping("/account/{accountId}")
	public ResponseEntity<GenricResponse<AccountEntity>> getAccount(@PathVariable("accountId") Long accountId){
		var genericResponse = new GenricResponse<AccountEntity>();
		genericResponse.setResponse(bankingService.getAccount(accountId));
		genericResponse.setStatus(HttpStatus.OK.toString());
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping("/users")
	public ResponseEntity<GenricResponse<String>> postUser(@RequestBody GenericModel<UserDTO> userModel) {
		var genericResponse = new GenricResponse<String>();
		genericResponse.setResponse(bankingService.createOrUpdateUser(userModel));
		genericResponse.setStatus(HttpStatus.CREATED.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
	}
	
	@PostMapping("/accounts")
	public ResponseEntity<GenricResponse<String>> postAccount(@RequestBody GenericModel<AccountDTO> accountModel) {
		var genericResponse = new GenricResponse<String>();
		genericResponse.setResponse(bankingService.createOrUpdateAccount(accountModel));
		genericResponse.setStatus(HttpStatus.CREATED.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
	}

	public ResponseEntity<GenricResponse<String>> putUser(@RequestBody GenericModel<UserDTO> userModel){
		var genericResponse = new GenricResponse<String>();
		genericResponse.setResponse(bankingService.createOrUpdateUser(userModel));
		genericResponse.setStatus(HttpStatus.CREATED.toString());
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}
	@PutMapping("/accounts/{accountId}")
	public ResponseEntity<GenricResponse<String>> putAccount(@PathVariable("accountId") Long accountId, @RequestBody GenericModel<AccountDTO> accountModel) {
		var genericResponse = new GenricResponse<String>();
		genericResponse.setResponse(bankingService.createOrUpdateAccount(accountModel, accountId));
		genericResponse.setStatus(HttpStatus.CREATED.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
	}
	
	@DeleteMapping("/accounts/{accountId}")
	public ResponseEntity<GenricResponse<String>> deleteAccount(@PathVariable("accountId") Long accountId){
		var genericResponse = new GenricResponse<String>();
		genericResponse.setResponse(bankingService.deleteAccount(accountId));
		genericResponse.setStatus(HttpStatus.OK.toString());
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping("/accounts/deposit/{accountId}")
	@Deprecated(since = "2.0v")
	@ApiOperation(value="Deposit Api is deprecated")
	@ApiIgnore
	public ResponseEntity<GenricResponse<String>> depoistMoney(@PathVariable("accountId") Long accountId, @RequestBody Long depositMoney){
		var genericResponse = new GenricResponse<String>();
		genericResponse.setResponse(bankingService.deposit(accountId, depositMoney));
		genericResponse.setStatus(HttpStatus.OK.toString());
		return ResponseEntity.ok(genericResponse);
	}

	@PostMapping("/accounts/withdraw/{accountId}")
	@ApiOperation(value = "Api helps to Deposit/Withdraw")
	public ResponseEntity<GenricResponse<String>> withdrawMoney(@PathVariable("accountId") Long accountId, @RequestBody Long withdrawMoney){
		var genericResponse = new GenricResponse<String>();
		genericResponse.setResponse(bankingService.withdraw(accountId, withdrawMoney));
		genericResponse.setStatus(HttpStatus.OK.toString());
		return ResponseEntity.ok(genericResponse);
	}
}
