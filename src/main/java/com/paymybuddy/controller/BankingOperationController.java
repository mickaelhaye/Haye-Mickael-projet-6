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

/**
 * BankingOperationController is the class to manage web page call for
 * BankingOperation
 * 
 * @author Mickael Hayé
 */
@Controller
public class BankingOperationController {

	@Autowired
	private BankingOperationService bankingOperationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	/**
	 * manage the call to the web page to add money by default the value is 10
	 * 
	 * @param model = contains data to add money
	 * @return the web page bankingOpaeration add money
	 */
	@GetMapping("/bankingOperation/bankingOperation_add_money")
	public String bankingOperationAddMoney(Model model) {
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationAddMoneyModel bankingOperationAddMoney = new BankingOperationAddMoneyModel();
		bankingOperationAddMoney.setMoney(10);
		bankingOperationAddMoney.setBalance(accountService.balance());
		model.addAttribute("bankingOperationAddMoney", bankingOperationAddMoney);
		return "/bankingOperation/bankingOperation_add_money";
	}

	/**
	 * manage the data of the new bankingOperation add money
	 * 
	 * @param bankingOperationAddMoney = contains data for the operation
	 * @return the web page bankingOpaeration add money
	 */
	@PostMapping("/bankingOperation/bankingOperation_add_money_add")
	public String saveBankingOperationAddMoney(
			@ModelAttribute("bankingOperationAddMoney") BankingOperationAddMoneyModel bankingOperationAddMoney) {
		accountService.addMoney(bankingOperationAddMoney.getMoney(), userService.getUserEmailSession());
		BankingOperationModel bankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(bankingOperation, bankingOperationAddMoney.getMoney(),
				bankingOperationAddMoney.getDescription(), "", userService.getUserEmailSession(), "add money");
		return "redirect:/bankingOperation/bankingOperation_add_money";
	}

	/**
	 * manage the call to the web page to send money by default the value is 10
	 * 
	 * @param model = contains data to send money
	 * @return the web page bankingOpaeration send money
	 */
	@GetMapping("/bankingOperation/bankingOperation_send_money")
	public String bankingOperationSendMoney(Model model) {
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationSendMoneyModel bankingOperationSendMoney = new BankingOperationSendMoneyModel();
		bankingOperationSendMoney.setMoney(10);
		bankingOperationSendMoney.setBalance(accountService.balance());
		model.addAttribute("bankingOperationSendMoney", bankingOperationSendMoney);
		return "/bankingOperation/bankingOperation_send_money";
	}

	/**
	 * manage the data of the new bankingOperation send money
	 * 
	 * @param bankingOperationSendMoney = contains data to send money
	 * @return the web page bankingOpaeration send money
	 */
	@PostMapping("/bankingOperation/bankingOperation_send_money_send")
	public String saveBankingOperationSendMoney(
			@ModelAttribute("bankingOperationSendMoney") BankingOperationSendMoneyModel bankingOperationSendMoney) {

		if (bankingOperationSendMoney.getMoney() > accountService.balance()) {
			return "/bankingOperation/bankingOperation_not_enough_money";
		}

		if (!userService.buddyExists(bankingOperationSendMoney.getBuddy(), userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_not_your_buddy";
		}

		if (!accountService.userHaveAccount(bankingOperationSendMoney.getBuddy())) {
			return "/bankingOperation/bankingOperation_buddy_no_account";
		}

		accountService.delMoney(bankingOperationSendMoney.getMoney());
		accountService.addMoney(bankingOperationSendMoney.getMoney(), bankingOperationSendMoney.getBuddy());

		BankingOperationModel sendBankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(sendBankingOperation, bankingOperationSendMoney.getMoney(),
				bankingOperationSendMoney.getDescription(), userService.getUserEmailSession(),
				bankingOperationSendMoney.getBuddy(),
				"send money to " + bankingOperationSendMoney.getBuddy() + " from " + userService.getUserEmailSession());

		return "/bankingOperation/bankingOperation_successfull";
	}

	/**
	 * manage the call to the web page to bankingOperation history
	 * 
	 * @param model = contains list of history from the user
	 * @return the web page bankingOperation_history
	 */
	@GetMapping("/bankingOperation/bankingOperation_history")
	public String bankingOperationHistory(Model model) {
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		return findPaginated(1, model);
	}

	/**
	 * @param pageNo = page to be displayed
	 * @param model  = = contains list of history from the user
	 * @return the web page bankingOperation_history
	 */
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
