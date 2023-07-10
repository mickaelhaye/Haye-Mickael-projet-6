package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.service.AccountService;

@Controller
public class AccountControler {

	@Autowired
	private AccountService accountService;

	@GetMapping("/account/account_create")
	public String userAddAccount(Model model) {
		// create account object to hold user form data
		AccountModel account = new AccountModel();
		model.addAttribute("account", account);
		return "/account/account_create";
	}

	@PostMapping("/accounts") // à valider
	public String saveAccount(@ModelAttribute("account") AccountModel account, Authentication authentification) {
		try {
			accountService.addAccountToUser(account, authentification.getName());
		} catch (Exception e) {

			e.printStackTrace();
			return "/account/account_already_exist";
		}

		return "/account/account_create_successfull";

	}

}
