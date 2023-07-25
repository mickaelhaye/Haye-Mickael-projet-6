package com.paymybuddy.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.UserService;

/**
 * accountService is the class to manage service for account entity
 * 
 * @author Mickael Hayé
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserService userService;

	/**
	 * recovers all accounts
	 */
	@Override
	public Iterable<AccountModel> getAccounts() {
		logger.debug("getAccounts");
		return accountRepository.findAll();
	}

	/**
	 * recovers account by an id
	 */
	@Override
	public Optional<AccountModel> getAccountById(Integer id) {
		logger.debug("getAccountById id=" + id);
		return accountRepository.findById(id);
	}

	/**
	 * add an account
	 */
	@Override
	public AccountModel addAccount(AccountModel account) {
		logger.debug("addAccount account=" + account);
		return accountRepository.save(account);
	}

	/**
	 * delete an account
	 */
	@Override
	public void delAccount(AccountModel account) {
		logger.debug("delAccount account=" + account);
		accountRepository.delete(account);

	}

	/**
	 * add account to user
	 */
	@Override
	public void addAccountToUser(AccountModel account, String email) throws Exception {
		logger.debug("addAccountToUser account=" + account + " email=" + email);
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

	/**
	 * delete account to user
	 */
	@Override
	public void delAccount(String accountName) {
		logger.debug("delAccount accountName=" + accountName);
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

	/**
	 * to know if an account name exist fom an user
	 */
	@Override
	public boolean AccountExistFromUser(String nameAccount, int userId) {
		logger.debug("AccountExistFromUser nameAccount=" + nameAccount + " userId=" + userId);
		return accountRepository.findByUserIdAndName(userId, nameAccount).isPresent();
	}

	/**
	 * to know if an user have an account
	 */
	@Override
	public boolean userHaveAccount(String userEmail) {
		logger.debug("userHaveAccount userEmail=" + userEmail);
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();
		if (user.getAccounts().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * to add money to an account
	 */
	@Override
	public void addMoney(float money, String userEmail) {
		logger.debug("addMoney money=" + money + " userEmail=" + userEmail);
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();

		AccountModel account = user.getAccounts().get(0);
		account.setBalance(account.getBalance() + money);
		addAccount(account);

	}

	/**
	 * to delete money to an account
	 */
	@Override
	public void delMoney(float money) {
		logger.debug("delMoney money=" + money);
		UserModel user = userService.getUserByEmail();
		AccountModel account = user.getAccounts().get(0);
		account.setBalance(account.getBalance() - money);
		addAccount(account);

	}

	/**
	 * to know how much an user have in his account
	 */
	@Override
	public float balance() {
		logger.debug("balance");
		UserModel user = userService.getUserByEmail();
		AccountModel account = user.getAccounts().get(0);
		return account.getBalance();
	}

	@Override
	/**
	 * account list for an user
	 */
	public List<AccountModel> accountListfromUser() {
		logger.debug("accountListfromUser");
		UserModel user = userService.getUserByEmail();
		return user.getAccounts();
	}
}
