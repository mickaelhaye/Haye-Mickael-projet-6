package com.paymybuddy.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.BankingOperationModel;
import com.paymybuddy.repository.BankingOperationRepository;
import com.paymybuddy.service.BankingOperationService;

@Service
public class BankingOperationServiceImpl implements BankingOperationService {

	@Autowired
	private BankingOperationRepository bankingOperationRepository;

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

}
