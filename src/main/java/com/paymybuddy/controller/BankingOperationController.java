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
import com.paymybuddy.service.UserService;

@Controller
public class BankingOperationController {

	@Autowired
	private BankingOperationService bankingOperationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	@GetMapping("/bankingOperation/bankingOperation_add_money")
	public String bankingOperationAddMoney(Model model, Authentication authentification) {
		if (!accountService.userHaveAccount(authentification.getName())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		RecupValueModel recupValue = new RecupValueModel();
		recupValue.setFloatValue1(10);
		recupValue.setFloatValue2(accountService.balance(authentification.getName()));
		model.addAttribute("recupValue", recupValue);
		return "/bankingOperation/bankingOperation_add_money";
	}

	@PostMapping("bankingOperation_add_money")
	public String saveBankingOperationAddMoney(@ModelAttribute("recupValue") RecupValueModel recupValue,
			Authentication authentification) {
		accountService.addMoney(recupValue.getFloatValue1(), authentification.getName());
		BankingOperationModel bankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(bankingOperation, recupValue.getFloatValue1(),
				recupValue.getStringValue1(), authentification.getName(), "add money");
		return "redirect:/bankingOperation/bankingOperation_add_money";
	}

	@GetMapping("/bankingOperation/bankingOperation_send_money")
	public String bankingOperationSendMoney(Model model, Authentication authentification) {
		if (!accountService.userHaveAccount(authentification.getName())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		RecupValueModel recupValue = new RecupValueModel();
		recupValue.setFloatValue1(10);
		recupValue.setFloatValue2(accountService.balance(authentification.getName()));
		model.addAttribute("recupValue", recupValue);
		return "/bankingOperation/bankingOperation_send_money";
	}

	@PostMapping("bankingOperation_send_money")
	public String saveBankingOperationSendMoney(@ModelAttribute("recupValue") RecupValueModel recupValue,
			Authentication authentification) {

		if (recupValue.getFloatValue1() > accountService.balance(authentification.getName())) {
			return "/bankingOperation/bankingOperation_not_enough_money";
		}

		if (!userService.buddyExists(recupValue.getStringValue2(), authentification.getName())) {
			return "/bankingOperation/bankingOperation_not_your_buddy";
		}

		accountService.delMoney(recupValue.getFloatValue1(), authentification.getName());
		accountService.addMoney(recupValue.getFloatValue1(), recupValue.getStringValue2());

		BankingOperationModel sendBankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(sendBankingOperation, recupValue.getFloatValue1(),
				recupValue.getStringValue1(), authentification.getName(),
				"send money to " + recupValue.getStringValue2());

		BankingOperationModel receiveBankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(receiveBankingOperation, recupValue.getFloatValue1(),
				recupValue.getStringValue1(), recupValue.getStringValue2(),
				"receive money from " + authentification.getName());

		return "/bankingOperation/bankingOperation_successfull";
	}

}
