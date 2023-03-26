package com.bank.training.service;

import java.lang.reflect.Field;
import java.util.Date;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.training.entity.AccountDTO;
import com.bank.training.entity.AccountEntity;
import com.bank.training.entity.UserDTO;
import com.bank.training.entity.UserEntity;
import com.bank.training.exception.AccountNotFoundException;
import com.bank.training.exception.EmptyUserObject;
import com.bank.training.exception.InsufficientFundsException;
import com.bank.training.exception.InvalidMoneyRequest;
import com.bank.training.exception.UserNotFoundException;
import com.bank.training.model.GenericModel;
import com.bank.training.repository.AccountRepository;
import com.bank.training.repository.UserRepository;

@Service
public class BankingService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AccountRepository accountRepo;

	public UserEntity getUser(Long userId) {
		var optUser = userRepo.findById(userId);
		UserEntity user = null;
		if (!optUser.isPresent()) {
			throwUserNotDefined(userId);
		} else {
			user = optUser.get();
		}
		return user;
	}

	public AccountEntity getAccount(Long accountId) {
		var optAccount = accountRepo.findById(accountId);
		AccountEntity account = null;
		if (!optAccount.isPresent()) {
			throwAccountNotDefined(accountId);
		} else {
			account = optAccount.get();
		}
		return account;
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public String createOrUpdateUser(GenericModel<UserDTO> userDTO) {
		var user = copyProperties(userDTO.getEntity(), new UserEntity());
		if (user == null) {
			throw new EmptyUserObject("No User data defined in request!!");
		} else if (user.getUserId() != 0 && !userRepo.findById(userDTO.getUserId()).isPresent()) {
			throwUserNotDefined(user.getUserId());
		}
		var savedUser = userRepo.save(user);
		return "User has been successfully saved with id: " + savedUser.getUserId();
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public String createOrUpdateAccount(GenericModel<AccountDTO> model, Object... args) {
		var account = new AccountEntity();
		if(args !=null && args.length != 0) {
			account = copyProperties(model.getEntity(), new AccountEntity(), "balance");
		} else {
			account = copyProperties(model.getEntity(), new AccountEntity());
		}
		var userId = model.getEntity().getUserId();
		var optUser = userRepo.findById(userId);
		var responseMsg = "";
		if (!optUser.isPresent()) {
			throwUserNotDefined(userId);
		}
		AccountEntity accountEntity = account;
		if (args != null && args.length != 0) {
			var id = (Long) args[0];
			responseMsg = "Account: " + args[0] + " is updated successfully!";
			var optEntity = accountRepo.findById(id);
			if(!optEntity.isPresent()) {
				throwAccountNotDefined(id);
			}
			try {
				accountEntity = injectAccountDetails(account, optEntity.get());
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			
		} else {
			accountEntity.setCreationDate(new Date());
		}
		accountEntity.setUserId(optUser.get());

		var saved = accountRepo.save(accountEntity);
		return responseMsg.isEmpty() ? "Account " + saved.getAccountNumber() + " was created successfully"
				: responseMsg;
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public String deleteAccount(Long accountId) {
		if(!accountRepo.findById(accountId).isPresent()) {
			throwAccountNotDefined(accountId);
		}
		accountRepo.deleteById(accountId);
		return "Account " + accountId + " was deleted successfully";
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public String deposit(Long accountId, Long depositedMoney) {
		var optAccount = accountRepo.findById(accountId);
		AccountEntity account = null;
		if (!optAccount.isPresent()) {
			throwAccountNotDefined(accountId);
		} else {
			account = optAccount.get();
			account.setBalance(account.getBalance() + depositedMoney);
			accountRepo.save(account);
		}
		return "Account balance was updated and current balance " + account.getBalance();
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	public String withdraw(Long accountId, Long withdrawMoney) {
		var optAccount = accountRepo.findById(accountId);
		var sb = new StringBuilder();
		AccountEntity account = null;
		if (!optAccount.isPresent()) {
			throwAccountNotDefined(accountId);
		} else if (withdrawMoney < 0 &&optAccount.get().getBalance() < (-withdrawMoney)) {
			throw new InsufficientFundsException(
					String.format("Unable to withdraw your account balance is low, current balance: %s",
							optAccount.get().getBalance()));
		} else if(withdrawMoney == 0) {
			throw new InvalidMoneyRequest("Please check input...");
		} else {
			account = optAccount.get();
			account.setBalance(account.getBalance() + withdrawMoney);
			accountRepo.save(account);
			sb.append("Account balance was updated, current balance: " + account.getBalance());
		}
		return withdrawMoney < 0 ? sb.append(", please collect your cash.").toString() : sb.append(", deposit successfull").toString();
	}

	private void throwUserNotDefined(Long userId) {
		throw new UserNotFoundException(String.format("User Not Found for Id: %s", userId));
	}

	private void throwAccountNotDefined(Long accountId) {
		throw new AccountNotFoundException(String.format("Account Not Found for Id: %s", accountId));
	}
	
	@SuppressWarnings("squid:S3011")
	private AccountEntity injectAccountDetails(AccountEntity... accounts) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		AccountEntity tableAccount = new AccountEntity();
		if(accounts!=null && accounts.length==2) {
			var updatingAccount = accounts[0];
			var tableClass = accounts[1].getClass();
			tableAccount = accounts[1];
			for(Field field : AccountEntity.class.getDeclaredFields()) {
				field.setAccessible(true);
				Object value = field.get(updatingAccount);
				if (value!=null)
				{
					var tableField = tableClass.getDeclaredField(field.getName());
					tableField.setAccessible(true);
					tableField.set(tableAccount, value);
				}
			}
		}
		return tableAccount;
	}
	
	private <T,S>T copyProperties(S sourceType, T targetType, String ...ignoreProperties ) {
		BeanUtils.copyProperties(sourceType, targetType, ignoreProperties);
		return targetType;
	}
}
