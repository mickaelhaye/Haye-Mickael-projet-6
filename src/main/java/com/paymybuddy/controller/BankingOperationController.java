package com.paymybuddy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(BankingOperationController.class);

	@Autowired
	private BankingOperationService bankingOperationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	/**
	 * manage the call to the web page to send money to my extern account
	 * 
	 * @param model = contains data to add money
	 * @return the web page bankingOpaeration send money to my extern account
	 */
	@GetMapping("/bankingOperation/bankingOperation_send_bank_extern_account")
	public String bankingOperationSendMoneyToMyExternAcccount(Model model) {
		logger.debug("bankingOperationSendMoneyToMyExternAcccount");
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			logger.info("/bankingOperation/bankingOperation_account_not_created");
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationAddMoneyModel bankingOperationAddMoney = new BankingOperationAddMoneyModel();
		bankingOperationAddMoney.setBalance(accountService.balance());
		model.addAttribute("bankingOperationAddMoney", bankingOperationAddMoney);
		logger.info("/bankingOperation/bankingOperation_send_bank_extern_account");
		return "/bankingOperation/bankingOperation_send_bank_extern_account";
	}

	/**
	 * manage the data of the new bankingOperation send money to my extern account
	 * 
	 * @param bankingOperationAddMoney = contains data for the operation
	 * @return the web page bankingOpaeration send money to my extern account
	 */
	@PostMapping("/bankingOperation/bankingOperation_send_money_extern_account")
	public String saveBankingOperationSendMoneyExternAccount(
			@ModelAttribute("bankingOperationAddMoney") BankingOperationAddMoneyModel bankingOperationAddMoney) {
		logger.debug("saveBankingOperationAddMoney bankingOperationAddMoney=" + bankingOperationAddMoney);
		if (bankingOperationAddMoney.getMoney() > accountService.balance()) {
			logger.info("/bankingOperation/bankingOperation_not_enough_money_extern_account");
			return "/bankingOperation/bankingOperation_not_enough_money_extern_account";
		}
		accountService.delMoney(bankingOperationAddMoney.getMoney());
		BankingOperationModel bankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(bankingOperation, bankingOperationAddMoney.getMoney(),
				bankingOperationAddMoney.getDescription(), "", userService.getUserEmailSession(),
				"send money to my extern account");
		logger.info("/bankingOperation/bankingOperation_successfull");
		return "/bankingOperation/bankingOperation_successfull";
	}

	/**
	 * manage the call to the web page to add money by default the value is 10
	 * 
	 * @param model = contains data to add money
	 * @return the web page bankingOpaeration add money
	 */
	@GetMapping("/bankingOperation/bankingOperation_add_money")
	public String bankingOperationAddMoney(Model model) {
		logger.debug("bankingOperationAddMoney");
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			logger.info("/bankingOperation/bankingOperation_account_not_created");
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationAddMoneyModel bankingOperationAddMoney = new BankingOperationAddMoneyModel();
		bankingOperationAddMoney.setMoney(10);
		bankingOperationAddMoney.setBalance(accountService.balance());
		model.addAttribute("bankingOperationAddMoney", bankingOperationAddMoney);
		logger.info("/bankingOperation/bankingOperation_add_money");
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
		logger.debug("saveBankingOperationAddMoney bankingOperationAddMoney=" + bankingOperationAddMoney);
		accountService.addMoney(bankingOperationAddMoney.getMoney(), userService.getUserEmailSession());
		BankingOperationModel bankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(bankingOperation, bankingOperationAddMoney.getMoney(),
				bankingOperationAddMoney.getDescription(), "", userService.getUserEmailSession(), "add money");
		logger.info("redirect:/bankingOperation/bankingOperation_add_money");
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
		logger.debug("bankingOperationSendMoney");
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			logger.info("/bankingOperation/bankingOperation_account_not_created");
			return "/bankingOperation/bankingOperation_account_not_created";
		}
		BankingOperationSendMoneyModel bankingOperationSendMoney = new BankingOperationSendMoneyModel();
		bankingOperationSendMoney.setMoney(10);
		bankingOperationSendMoney.setBalance(accountService.balance());
		model.addAttribute("bankingOperationSendMoney", bankingOperationSendMoney);
		logger.info("/bankingOperation/bankingOperation_send_money");
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
		logger.debug("saveBankingOperationSendMoney bankingOperationSendMoney=" + bankingOperationSendMoney);
		if (bankingOperationSendMoney.getMoney() > accountService.balance()) {
			logger.info("/bankingOperation/bankingOperation_not_enough_money");
			return "/bankingOperation/bankingOperation_not_enough_money";
		}

		if (!userService.buddyExists(bankingOperationSendMoney.getBuddy(), userService.getUserEmailSession())) {
			logger.info("/bankingOperation/bankingOperation_not_your_buddy");
			return "/bankingOperation/bankingOperation_not_your_buddy";
		}

		if (!accountService.userHaveAccount(bankingOperationSendMoney.getBuddy())) {
			logger.info("/bankingOperation/bankingOperation_buddy_no_account");
			return "/bankingOperation/bankingOperation_buddy_no_account";
		}

		accountService.delMoney(bankingOperationSendMoney.getMoney());
		accountService.addMoney(bankingOperationSendMoney.getMoney(), bankingOperationSendMoney.getBuddy());

		BankingOperationModel sendBankingOperation = new BankingOperationModel();
		bankingOperationService.addBankingOperationToAccount(sendBankingOperation, bankingOperationSendMoney.getMoney(),
				bankingOperationSendMoney.getDescription(), userService.getUserEmailSession(),
				bankingOperationSendMoney.getBuddy(),
				"send money to " + bankingOperationSendMoney.getBuddy() + " from " + userService.getUserEmailSession());
		logger.info("/bankingOperation/bankingOperation_successfull");
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
		logger.debug("bankingOperationHistory");
		if (!accountService.userHaveAccount(userService.getUserEmailSession())) {
			logger.info("/bankingOperation/bankingOperation_account_not_created");
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
		logger.debug("findPaginated pageNo=" + pageNo);
		int pageSize = 3;
		Page<BankingOperationModel> page = bankingOperationService.findPaginated(pageNo, pageSize);
		List<BankingOperationModel> ListBankingOperations = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("bankingoperations", ListBankingOperations);
		logger.info("/bankingOperation/bankingOperation_history");
		return "/bankingOperation/bankingOperation_history";

	}
}
