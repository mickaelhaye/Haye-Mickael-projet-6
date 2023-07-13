package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.dto.BankingOperationAddMoneyModel;
import com.paymybuddy.model.dto.BankingOperationSendMoneyModel;
import com.paymybuddy.model.entity.BankingOperationModel;
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

	@Autowired
	private UserController userController;

	@GetMapping("/bankingOperation/bankingOperation_add_money")
	public String bankingOperationAddMoney(Model model) {
		if (!accountService.userHaveAccount(userController.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationAddMoneyModel bankingOperationAddMoney = new BankingOperationAddMoneyModel();
		bankingOperationAddMoney.setMoney(10);
		bankingOperationAddMoney.setBalance(accountService.balance(userController.getUserEmailSession()));
		model.addAttribute("bankingOperationAddMoney", bankingOperationAddMoney);
		return "/bankingOperation/bankingOperation_add_money";
	}

	@PostMapping("bankingOperation_add_money")
	public String saveBankingOperationAddMoney(
			@ModelAttribute("bankingOperationAddMoney") BankingOperationAddMoneyModel bankingOperationAddMoney) {
		accountService.addMoney(bankingOperationAddMoney.getMoney(), userController.getUserEmailSession());
		BankingOperationModel bankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(bankingOperation, bankingOperationAddMoney.getMoney(),
				bankingOperationAddMoney.getDescription(), userController.getUserEmailSession(), "add money");
		return "redirect:/bankingOperation/bankingOperation_add_money";
	}

	@GetMapping("/bankingOperation/bankingOperation_send_money")
	public String bankingOperationSendMoney(Model model) {
		if (!accountService.userHaveAccount(userController.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationSendMoneyModel bankingOperationSendMoney = new BankingOperationSendMoneyModel();
		bankingOperationSendMoney.setMoney(10);
		bankingOperationSendMoney.setBalance(accountService.balance(userController.getUserEmailSession()));
		model.addAttribute("bankingOperationSendMoney", bankingOperationSendMoney);
		return "/bankingOperation/bankingOperation_send_money";
	}

	@PostMapping("bankingOperation_send_money")
	public String saveBankingOperationSendMoney(
			@ModelAttribute("bankingOperationSendMoney") BankingOperationSendMoneyModel bankingOperationSendMoney) {

		if (bankingOperationSendMoney.getMoney() > accountService.balance(userController.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_not_enough_money";
		}

		if (!userService.buddyExists(bankingOperationSendMoney.getBuddy(), userController.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_not_your_buddy";
		}

		accountService.delMoney(bankingOperationSendMoney.getMoney(), userController.getUserEmailSession());
		accountService.addMoney(bankingOperationSendMoney.getMoney(), bankingOperationSendMoney.getBuddy());

		BankingOperationModel sendBankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(sendBankingOperation, bankingOperationSendMoney.getMoney(),
				bankingOperationSendMoney.getDescription(), userController.getUserEmailSession(),
				"send money to " + bankingOperationSendMoney.getBuddy());

		BankingOperationModel receiveBankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(receiveBankingOperation,
				bankingOperationSendMoney.getMoney(), bankingOperationSendMoney.getDescription(),
				bankingOperationSendMoney.getBuddy(), "receive money from " + userController.getUserEmailSession());

		return "/bankingOperation/bankingOperation_successfull";
	}

}
