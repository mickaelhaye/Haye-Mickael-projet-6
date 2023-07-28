package com.paymybuddy.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.UserService;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
class AccountServiceTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	@Test
	void addAndDelAccountToUserTest() {

		userService.setUserEmailSession("UserWithoutCount@gmail.com");
		List<AccountModel> accounts = (List<AccountModel>) accountService.getAccounts();
		int accountsCount = accounts.size();
		AccountModel account = new AccountModel();
		account.setName("accountTest");
		try {
			accountService.addAccountToUser(account, "UserWithoutCount@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<AccountModel> accountsNew = (List<AccountModel>) accountService.getAccounts();
		int accountsNewCount = accountsNew.size();
		assertEquals(accountsCount + 1, accountsNewCount);

		accountService.delAccount("accountTest");
		accountsNew = (List<AccountModel>) accountService.getAccounts();
		accountsNewCount = accountsNew.size();
		assertEquals(accountsCount, accountsNewCount);
	}

	@Test
	void addAccountAlreadyExistToUserTest() {

		userService.setUserEmailSession("John.boyd@gmail.com");
		List<AccountModel> accounts = (List<AccountModel>) accountService.getAccounts();
		int accountsCount = accounts.size();

		AccountModel account = new AccountModel();
		account.setName("mon compte John");
		try {
			accountService.addAccountToUser(account, "John.boyd@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			List<AccountModel> accountsNew = (List<AccountModel>) accountService.getAccounts();
			int accountsNewCount = accountsNew.size();
			assertEquals(accountsCount, accountsNewCount);
		}
	}

	@Test
	void userHaveAccountTest() {
		assertTrue(accountService.userHaveAccount("John.boyd@gmail.com"));
		assertFalse(accountService.userHaveAccount("UserWithoutCount@gmail.com"));
	}

	@Test
	void userAddDelMoneyTest() {
		userService.setUserEmailSession("John.boyd@gmail.com");
		float balance = accountService.balance();
		accountService.addMoney(500, "John.boyd@gmail.com");
		float newBalance = accountService.balance();
		assertNotEquals(balance, newBalance);
		accountService.delMoney(200);
		newBalance = accountService.balance();
		assertEquals(balance + (500 * 0.95) - 200, newBalance);
	}

	@Test
	void accountListFromUserTest() {
		userService.setUserEmailSession("John.boyd@gmail.com");
		assertEquals(1, accountService.accountListfromUser().size());
	}

	@Test
	void getAccountByIdTest() {
		Optional<AccountModel> optAccount = accountService.getAccountById(1);
		AccountModel account = optAccount.get();
		assertEquals("moncomptelily", account.getName());

	}

}
