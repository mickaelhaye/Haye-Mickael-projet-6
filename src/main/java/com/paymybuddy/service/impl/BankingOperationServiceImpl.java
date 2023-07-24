package com.paymybuddy.service.impl;

import java.util.List;
import java.util.Optional;

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
		return bankingOperationRepository.findAll();
	}

	/**
	 * recovers bankingOperations by an id
	 */
	@Override
	public Optional<BankingOperationModel> getBankingOperationById(Integer id) {
		return bankingOperationRepository.findById(id);
	}

	/**
	 * add an banking operation
	 */
	@Override
	public BankingOperationModel addBankingOperation(BankingOperationModel bankingOperation) {
		return bankingOperationRepository.save(bankingOperation);
	}

	/**
	 * delete a banking operation
	 */
	@Override
	public void delBankingOperation(BankingOperationModel bankingOperation) {
		bankingOperationRepository.delete(bankingOperation);

	}

	/**
	 * ada a banking operation to an account
	 */
	@Override
	public void addBankingOperationToAccount(BankingOperationModel bankingOperation, float amount, String description,
			String userBuddy, String userEmail, String typeTransation) {
		bankingOperation.setAmount(amount);
		bankingOperation.setDescription(description);
		bankingOperation.setDate(calendarService.getDate());
		bankingOperation.setHour(calendarService.getHour());
		bankingOperation.setTypeTransaction(typeTransation);

		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();

		AccountModel account = user.getAccounts().get(0);
		account.setBankingOperationsToList(bankingOperation);

		if (userBuddy != "") {
			Optional<UserModel> OptBuddy = userService.getUserByEmail(userBuddy);
			UserModel buddy = OptBuddy.get();

			AccountModel accountbuddy = buddy.getAccounts().get(0);
			accountbuddy.setBankingOperationsToList(bankingOperation);
		}

		try {
			addBankingOperation(bankingOperation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * banking operation list from an account
	 */
	@Override
	public List<BankingOperationModel> bankingOperationListfromUser(String userEmail) {
		Optional<UserModel> OptUser = userService.getUserByEmail(userEmail);
		UserModel user = OptUser.get();
		return user.getAccounts().get(0).getBankingOperations();
	}

	/**
	 * pagination for the banking list of an account
	 */
	@Override
	public Page<BankingOperationModel> findPaginated(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return bankingOperationRepository.findByEmail(userService.getUserEmailSession(), pageable);
	}

}
