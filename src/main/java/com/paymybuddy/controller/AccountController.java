package com.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.UserService;

/**
 * accountController is the class to manage web page call for account
 * 
 * @author Mickael Hayé
 */

@Controller
public class AccountController {

	private static Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	/**
	 * manage the call to the web page to create a new account
	 * 
	 * @param model = contains data for an account
	 * @return the web page account create or account already exist
	 */
	@GetMapping("/account/account_create")
	public String userAddAccount(Model model) {
		logger.debug("userAddAccount");
		if (accountService.userHaveAccount(userService.getUserEmailSession())) {
			logger.info("account/account_already");
			return "account/account_already";
		}

		// create account object to hold user form data
		AccountModel account = new AccountModel();
		model.addAttribute("account", account);
		logger.info("/account/account_create");
		return "/account/account_create";
	}

	/**
	 * manage the call to the web page to send a new account
	 * 
	 * @param account = entity for new account
	 * @return the web page account create successfull or account already exist
	 */
	@PostMapping("/account/accounts")
	public String saveAccount(@ModelAttribute("account") AccountModel account) {
		logger.debug("saveAccount account=" + account);
		try {
			accountService.addAccountToUser(account, userService.getUserEmailSession());
		} catch (Exception e) {

			e.printStackTrace();
			logger.info("/account/account_already_exist");
			return "/account/account_already_exist";
		}
		logger.info("/account/account_create_successfull");
		return "/account/account_create_successfull";

	}

	/**
	 * manage the call to the web page to del an account
	 * 
	 * @param model = account list of the user
	 * @return the web page account del
	 */
	@GetMapping("/account/account_del")
	public String accountDel(Model model) {
		logger.debug("accountDel");
		model.addAttribute("accounts", accountService.accountListfromUser());
		logger.info("/account/account_del");
		return "/account/account_del";
	}

	/**
	 * manage the call to the web page to send the account deleting
	 * 
	 * @param name = name of account to be deleted
	 * @return the web page account del
	 */
	@GetMapping("/account/account_del_account/delete/{name}")
	public String accountDelete(@PathVariable String name) {
		logger.debug("accountDelete name=" + name);
		accountService.delAccount(name);
		logger.info("redirect:/account/account_del");
		return "redirect:/account/account_del";
	}

}
