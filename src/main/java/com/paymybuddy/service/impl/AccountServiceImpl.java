package com.paymybuddy.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserService userService;

	@Override
	public Iterable<AccountModel> getAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Optional<AccountModel> getAccountById(Integer id) {
		return accountRepository.findById(id);
	}

	@Override
	public AccountModel addAccount(AccountModel account) {
		return accountRepository.save(account);
	}

	@Override
	public void delAccount(AccountModel account) {
		accountRepository.delete(account);

	}

	@Override
	public void addAccountToUser(AccountModel account, String email) throws Exception {
		Optional<UserModel> OptUser = userService.getUserByEmail(email);
		UserModel user = OptUser.get();

		if (AccountExistFromUser(account.getName(), user.getUserId())) {
			throw new Exception("This account aleady exist: " + account.getName() + " " + user.getUserId());
		}

		user.setAccountToList(account);
		try {
			addAccount(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void delAccount(String accountName) {
		Optional<UserModel> OptUser = userService.getUserByEmail(userService.getUserEmailSession());
		UserModel user = OptUser.get();

		if (accountRepository.findByEmailAndAccountName(userService.getUserEmailSession(), accountName).isPresent()) {
			Optional<AccountModel> OptAccount = accountRepository
					.findByEmailAndAccountName(userService.getUserEmailSession(), accountName);
			AccountModel account = OptAccount.get();
			user.removeAccountToAccountList(account);
			delAccount(account);
		}

		try {
			userService.updateUser(user);
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean AccountExistFromUser(String nameAccount, int userId) {

		return accountRepository.findByUserIdAndName(userId, nameAccount).isPresent();
	}

	@Override
	public boolean userHaveAccount(String userEmail) {
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();
		if (user.getAccounts().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public void addMoney(float money, String userEmail) {
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();

		AccountModel account = user.getAccounts().get(0);
		account.setBalance(account.getBalance() + money);
		addAccount(account);

	}

	@Override
	public void delMoney(float money, String userEmail) {
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();

		AccountModel account = user.getAccounts().get(0);
		account.setBalance(account.getBalance() - money);
		addAccount(account);

	}

	@Override
	public float balance(String userEmail) {
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();

		AccountModel account = user.getAccounts().get(0);
		return account.getBalance();
	}

	@Override
	public List<AccountModel> accountListfromUser(String userEmail) {
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();
		return user.getAccounts();
	}
}
