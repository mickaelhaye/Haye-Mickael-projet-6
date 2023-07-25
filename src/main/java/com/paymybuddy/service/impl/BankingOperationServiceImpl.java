package com.paymybuddy.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.model.entity.BankingOperationModel;
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.repository.BankingOperationRepository;
import com.paymybuddy.service.BankingOperationService;
import com.paymybuddy.service.CalendarService;
import com.paymybuddy.service.UserService;

/**
 * bankingOperationService is the class to manage service for bankingOperation
 * entity
 * 
 * @author Mickael Hayé
 */
@Service
public class BankingOperationServiceImpl implements BankingOperationService {

	private static Logger logger = LoggerFactory.getLogger(BankingOperationServiceImpl.class);

	@Autowired
	private BankingOperationRepository bankingOperationRepository;

	@Autowired
	private CalendarService calendarService;

	@Autowired
	private UserService userService;

	/**
	 * recovers all bankingOperations
	 */
	@Override
	public Iterable<BankingOperationModel> getBankingOperations() {
		logger.debug("getBankingOperations");
		return bankingOperationRepository.findAll();
	}

	/**
	 * recovers bankingOperations by an id
	 */
	@Override
	public Optional<BankingOperationModel> getBankingOperationById(Integer id) {
		logger.debug("getBankingOperationById id=" + id);
		return bankingOperationRepository.findById(id);
	}

	/**
	 * add an banking operation
	 */
	@Override
	public BankingOperationModel addBankingOperation(BankingOperationModel bankingOperation) {
		logger.debug("addBankingOperation bankingOperation=" + bankingOperation);
		return bankingOperationRepository.save(bankingOperation);
	}

	/**
	 * delete a banking operation
	 */
	@Override
	public void delBankingOperation(BankingOperationModel bankingOperation) {
		logger.debug("delBankingOperation bankingOperation=" + bankingOperation);
		bankingOperationRepository.delete(bankingOperation);

	}

	/**
	 * ada a banking operation to an account
	 */
	@Override
	public void addBankingOperationToAccount(BankingOperationModel bankingOperation, float amount, String description,
			String userBuddy, String userEmail, String typeTransation) {
		logger.debug("addBankingOperationToAccount bankingOperation=" + bankingOperation + " amount=" + amount
				+ " description=" + description + " userBuddy=" + userBuddy + " userEmail=" + userEmail
				+ " typeTransaction=" + typeTransation);
		bankingOperation.setAmount(amount);
		bankingOperation.setDescription(description);
		bankingOperation.setDate(calendarService.getDate());
		bankingOperation.setHour(calendarService.getHour());
		bankingOperation.setTypeTransaction(typeTransation);
		// find the user
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();
		// find the account of the user
		AccountModel account = user.getAccounts().get(0);
		account.setBankingOperationsToList(bankingOperation);

		if (userBuddy != "") {
			// find the buddy
			Optional<UserModel> OptBuddy = userService.getUserByEmail(userBuddy);
			UserModel buddy = OptBuddy.get();
			// find the account of the buddy
			AccountModel accountbuddy = buddy.getAccounts().get(0);
			accountbuddy.setBankingOperationsToList(bankingOperation);
		}

		try {
			addBankingOperation(bankingOperation);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("add banking operation error" + e);
		}
	}

	/**
	 * banking operation list from an account
	 */
	@Override
	public List<BankingOperationModel> bankingOperationListfromUser(String userEmail) {
		logger.debug("bankingOperationListfromUser userEmail=" + userEmail);
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();
		return user.getAccounts().get(0).getBankingOperations();
	}

	/**
	 * pagination for the banking list of an account
	 */
	@Override
	public Page<BankingOperationModel> findPaginated(int pageNo, int pageSize) {
		logger.debug("findPaginated pageNo=" + pageNo + " pageSize=" + pageSize);
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return bankingOperationRepository.findByEmail(userService.getUserEmailSession(), pageable);
	}

}
