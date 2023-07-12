package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.entity.BankingOperationModel;
import com.paymybuddy.model.entity.RecupValueModel;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.BankingOperationService;

@Controller
public class BankingOperationController {

	@Autowired
	private BankingOperationService bankingOperationService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/bankingOperation/bankingOperation_add_money")
	public String bankingOperationAddMoney(Model model, Authentication authentification) {
		if (!accountService.userHaveAccount(authentification.getName())) {
			return "/bankingOperation/account_not_created";
		}
		RecupValueModel recupValue = new RecupValueModel();
		recupValue.setFloatValue1(10);
		recupValue.setFloatValue2(accountService.balance(authentification.getName()));
		model.addAttribute("recupValue", recupValue);
		return "/bankingOperation/bankingOperation_add_money";
	}

	@PostMapping("bankingOperation_add_money")
	public String saveBankingOperation(@ModelAttribute("recupValue") RecupValueModel recupValue,
			Authentication authentification) {
		accountService.addMoney(recupValue.getFloatValue1(), authentification.getName());
		BankingOperationModel bankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(bankingOperation, recupValue.getFloatValue1(),
				recupValue.getStringValue1(), authentification.getName());
		return "redirect:/bankingOperation/bankingOperation_add_money";
	}

}
