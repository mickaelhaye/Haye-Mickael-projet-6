package com.paymybuddy.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.model.entity.BankingOperationModel;
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.repository.BankingOperationRepository;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.BankingOperationService;
import com.paymybuddy.service.CalendarService;
import com.paymybuddy.service.UserService;

@Service
public class BankingOperationServiceImpl implements BankingOperationService {

	@Autowired
	private BankingOperationRepository bankingOperationRepository;

	@Autowired
	private CalendarService calendarService;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Override
	public Iterable<BankingOperationModel> getBankingOperations() {
		return bankingOperationRepository.findAll();
	}

	@Override
	public Optional<BankingOperationModel> getBankingOperationById(Integer id) {
		return bankingOperationRepository.findById(id);
	}

	@Override
	public BankingOperationModel addBankingOperation(BankingOperationModel bankingOperation) {
		return bankingOperationRepository.save(bankingOperation);
	}

	@Override
	public void delBankingOperation(BankingOperationModel bankingOperation) {
		bankingOperationRepository.delete(bankingOperation);

	}

	@Override
	public void addBankingOperationToAccount(BankingOperationModel bankingOperation, float amount, String description,
			String userEmail) {
		bankingOperation.setAmount(amount);
		bankingOperation.setDescription(description);
		bankingOperation.setDate(calendarService.getDate());
		bankingOperation.setHour(calendarService.getHour());
		bankingOperation.setTypeTransaction("Money Deposit");

		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();

		AccountModel account = user.getAccounts().get(0);
		account.setBankingOperationsToList(bankingOperation);
		try {
			accountService.addAccount(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
