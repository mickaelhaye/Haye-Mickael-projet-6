package com.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.paymybuddy.model.entity.BankingOperationModel;

public interface BankingOperationService {

	public Iterable<BankingOperationModel> getBankingOperations();

	public BankingOperationModel addBankingOperation(BankingOperationModel bankingOperation);

	public Optional<BankingOperationModel> getBankingOperationById(Integer id);

	public void delBankingOperation(BankingOperationModel bankingOperation);

	public void addBankingOperationToAccount(BankingOperationModel bankingOperation, float amount, String description,
			String userBuddy, String userEmail, String typeTransation);

	public List<BankingOperationModel> bankingOperationListfromUser(String userEmail);

	public Page<BankingOperationModel> findPaginated(int pageNo, int pageSize);

	public void DeleteBankingOperationOrphan();

}
