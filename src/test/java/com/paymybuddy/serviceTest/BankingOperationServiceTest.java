package com.paymybuddy.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.paymybuddy.model.entity.BankingOperationModel;
import com.paymybuddy.service.BankingOperationService;
import com.paymybuddy.service.UserService;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
class BankingOperationServiceTest {

	@Autowired
	private BankingOperationService bankingOperationService;

	@Autowired
	private UserService userService;

	@Test
	void addBankingOperationToAccountTest() {
		userService.setUserEmailSession("John.boyd@gmail.com");
		List<BankingOperationModel> bankingOperations = (List<BankingOperationModel>) bankingOperationService
				.getBankingOperations();
		int bankingOperationsCount = bankingOperations.size();
		BankingOperationModel bankingOperation = new BankingOperationModel();
		try {
			bankingOperationService.addBankingOperationToAccount(bankingOperation, 500, "test",
					"lily.cooper@hotmail.fr", "John.boyd@gmail.com", "send money");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<BankingOperationModel> bankingOperationsNew = (List<BankingOperationModel>) bankingOperationService
				.getBankingOperations();
		int bankingOperationsNewCount = bankingOperationsNew.size();
		assertEquals(bankingOperationsCount + 1, bankingOperationsNewCount);
	}

	@Test
	void bankingOperationListfromUserTest() {
		int bankingOperationsCount = bankingOperationService.bankingOperationListfromUser("John.boyd@gmail.com").size();
		addBankingOperationToAccountTest();
		int bankingOperationsNewCount = bankingOperationService.bankingOperationListfromUser("John.boyd@gmail.com")
				.size();
		assertEquals(bankingOperationsCount + 1, bankingOperationsNewCount);
	}

	@Test
	void getBankingOperationByIdTest() {
		Optional<BankingOperationModel> optBankingOperation = bankingOperationService.getBankingOperationById(1);
		BankingOperationModel bankingOperation = optBankingOperation.get();
		assertEquals("add 5000 to my account", bankingOperation.getDescription());
	}

	@Test
	void findPaginatedTest() {
		userService.setUserEmailSession("John.boyd@gmail.com");
		Page<BankingOperationModel> page = bankingOperationService.findPaginated(1, 2);
		List<BankingOperationModel> ListBankingOperations = page.getContent();
		assertEquals(2, ListBankingOperations.size());
	}

}
