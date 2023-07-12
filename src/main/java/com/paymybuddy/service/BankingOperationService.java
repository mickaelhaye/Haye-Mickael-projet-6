package com.paymybuddy.service;

import java.util.Optional;

import com.paymybuddy.model.entity.BankingOperationModel;

public interface BankingOperationService {

	public Iterable<BankingOperationModel> getBankingOperations();

	public BankingOperationModel addBankingOperation(BankingOperationModel bankingOperation);

	public Optional<BankingOperationModel> getBankingOperationById(Integer id);

	public void delBankingOperation(BankingOperationModel bankingOperation);

	public void addBankingOperationToAccount(BankingOperationModel bankingOperation, float amount, String description,
			String userEmail);

}
