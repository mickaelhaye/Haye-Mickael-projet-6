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

import jakarta.transaction.Transactional;

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
	@Transactional
	public Iterable<AccountModel> getAccounts() {
		logger.debug("getAccounts");
		return accountRepository.findAll();
	}

	/**
	 * recovers account by an id
	 */
	@Override
	@Transactional
	public Optional<AccountModel> getAccountById(Integer id) {
		logger.debug("getAccountById id=" + id);
		return accountRepository.findById(id);
	}

	/**
	 * add an account
	 */
	@Override
	@Transactional
	public AccountModel addAccount(AccountModel account) {
		logger.debug("addAccount account=" + account);
		return accountRepository.save(account);
	}

	/**
	 * delete an account
	 */
	@Override
	@Transactional
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
		// test if the user have already an account with the same name
		if (AccountExistFromUser(account.getName(), user.getUserId())) {
			throw new Exception("This account aleady exist: " + account.getName() + " " + user.getUserId());
		}
		// add the account to the user
		user.setAccountToList(account);
		try {
			addAccount(account);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("add account error" + e);
		}

	}

	/**
	 * delete account to user
	 */
	@Override
	@Transactional
	public void delAccount(String accountName) {
		logger.debug("delAccount accountName=" + accountName);
		Optional<UserModel> OptUser = userService.getUserByEmail(userService.getUserEmailSession());
		UserModel user = OptUser.get();
		// test if the account is present into the account list of the user
		if (accountRepository.findByEmailAndAccountName(userService.getUserEmailSession(), accountName).isPresent()) {
			Optional<AccountModel> OptAccount = accountRepository
					.findByEmailAndAccountName(userService.getUserEmailSession(), accountName);
			AccountModel account = OptAccount.get();
			user.removeAccountToAccountList(account);
			delAccount(account);
		}
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update user error" + e);
		}
	}

	/**
	 * to know if an account name exist fom an user
	 */
	@Override
	@Transactional
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
		// prelevement 0.05%
		money = (float) (money * 0.95);
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

	/**
	 * account list for an user
	 */
	@Override
	public List<AccountModel> accountListfromUser() {
		logger.debug("accountListfromUser");
		UserModel user = userService.getUserByEmail();
		return user.getAccounts();
	}
}
