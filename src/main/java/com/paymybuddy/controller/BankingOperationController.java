package com.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/bankingOperation/bankingOperation_add_money")
	public String bankingOperationAddMoney(Model model) {
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationAddMoneyModel bankingOperationAddMoney = new BankingOperationAddMoneyModel();
		bankingOperationAddMoney.setMoney(10);
		bankingOperationAddMoney.setBalance(accountService.balance(userService.getUserEmailSession()));
		model.addAttribute("bankingOperationAddMoney", bankingOperationAddMoney);
		return "/bankingOperation/bankingOperation_add_money";
	}

	@PostMapping("bankingOperation_add_money")
	public String saveBankingOperationAddMoney(
			@ModelAttribute("bankingOperationAddMoney") BankingOperationAddMoneyModel bankingOperationAddMoney) {
		accountService.addMoney(bankingOperationAddMoney.getMoney(), userService.getUserEmailSession());
		BankingOperationModel bankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(bankingOperation, bankingOperationAddMoney.getMoney(),
				bankingOperationAddMoney.getDescription(), "", userService.getUserEmailSession(), "add money");
		return "redirect:/bankingOperation/bankingOperation_add_money";
	}

	@GetMapping("/bankingOperation/bankingOperation_send_money")
	public String bankingOperationSendMoney(Model model) {
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationSendMoneyModel bankingOperationSendMoney = new BankingOperationSendMoneyModel();
		bankingOperationSendMoney.setMoney(10);
		bankingOperationSendMoney.setBalance(accountService.balance(userService.getUserEmailSession()));
		model.addAttribute("bankingOperationSendMoney", bankingOperationSendMoney);
		return "/bankingOperation/bankingOperation_send_money";
	}

	@PostMapping("bankingOperation_send_money")
	public String saveBankingOperationSendMoney(
			@ModelAttribute("bankingOperationSendMoney") BankingOperationSendMoneyModel bankingOperationSendMoney) {

		if (bankingOperationSendMoney.getMoney() > accountService.balance(userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_not_enough_money";
		}

		if (!userService.buddyExists(bankingOperationSendMoney.getBuddy(), userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_not_your_buddy";
		}

		if (!accountService.userHaveAccount(bankingOperationSendMoney.getBuddy())) {
			return "/bankingOperation/bankingOperation_buddy_no_account";
		}

		accountService.delMoney(bankingOperationSendMoney.getMoney(), userService.getUserEmailSession());
		accountService.addMoney(bankingOperationSendMoney.getMoney(), bankingOperationSendMoney.getBuddy());

		BankingOperationModel sendBankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(sendBankingOperation, bankingOperationSendMoney.getMoney(),
				bankingOperationSendMoney.getDescription(), userService.getUserEmailSession(),
				bankingOperationSendMoney.getBuddy(),
				"send money to " + bankingOperationSendMoney.getBuddy() + " from " + userService.getUserEmailSession());

		return "/bankingOperation/bankingOperation_successfull";
	}

	@GetMapping("/bankingOperation/bankingOperation_history")
	public String accountDel(Model model) {
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		return findPaginated(1, model);
	}

	@GetMapping("/bankingOperation/bankingOperation_history/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
		int pageSize = 4;
		Page<BankingOperationModel> page = bankingOperationService.findPaginated(pageNo, pageSize);
		List<BankingOperationModel> ListBankingOperations = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("bankingoperations", ListBankingOperations);
		return "/bankingOperation/bankingOperation_history";

	}
}
