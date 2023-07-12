package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.model.entity.BankingOperationModel;
import com.paymybuddy.service.BankingOperationService;

@RestController
public class BankingOperationControllerOld {

	@Autowired
	private BankingOperationService bankingOperationService;

	@GetMapping("/bankingOperation")
	public Iterable<BankingOperationModel> displayBankingOperationList() {
		return bankingOperationService.getBankingOperations();
	}

	@PostMapping("/bankingOperation")
	public void addBankingOperation(@RequestBody BankingOperationModel bankingOperation) {
		bankingOperationService.addBankingOperation(bankingOperation);
	}

	@PatchMapping("/bankingOperation")
	public void updateBankingOperation(@RequestBody BankingOperationModel bankingOperation) {
		bankingOperationService.addBankingOperation(bankingOperation);
	}

	@DeleteMapping("/bankingOperation")
	public void deleteBankingOperation(@RequestBody BankingOperationModel bankingOperation) {
		bankingOperationService.delBankingOperation(bankingOperation);
	}
}
