package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.service.AccountService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserController userController;

	@GetMapping("/account/account_create")
	public String userAddAccount(Model model) {
		if (accountService.userHaveAccount(userController.getUserEmailSession())) {
			return "account/account_already";
		}

		// create account object to hold user form data
		AccountModel account = new AccountModel();
		model.addAttribute("account", account);
		return "/account/account_create";
	}

	@PostMapping("/accounts") // à valider
	public String saveAccount(@ModelAttribute("account") AccountModel account) {
		try {
			accountService.addAccountToUser(account, userController.getUserEmailSession());
		} catch (Exception e) {

			e.printStackTrace();
			return "/account/account_already_exist";
		}

		return "/account/account_create_successfull";

	}

	@GetMapping("/account/account_del")
	public String accountDel(Model model) {
		model.addAttribute("accounts", accountService.accountListfromUser(userController.getUserEmailSession()));
		return "/account/account_del";
	}

	@GetMapping("/account/account_del_account/delete/{name}")
	public String accountDelete(@PathVariable String name) {
		accountService.delAccount(name, userController.getUserEmailSession());
		return "redirect:/account/account_del";
	}

}
