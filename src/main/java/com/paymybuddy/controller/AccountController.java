package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/account")
	public Iterable<AccountModel> displayAccountList() {
		return accountService.getAccounts();
	}

	@PostMapping("/account")
	public void addAccount(@RequestBody AccountModel account) {
		accountService.addAccount(account);
	}

	@PatchMapping("/account")
	public void updateAccount(@RequestBody AccountModel account) {
		accountService.addAccount(account);
	}

	@DeleteMapping("/account")
	public void deleteAccount(@RequestBody AccountModel account) {
		accountService.delAccount(account);
	}

}
