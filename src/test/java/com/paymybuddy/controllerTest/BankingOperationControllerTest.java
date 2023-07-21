package com.paymybuddy.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.paymybuddy.model.dto.BankingOperationAddMoneyModel;
import com.paymybuddy.model.dto.BankingOperationSendMoneyModel;
import com.paymybuddy.service.UserService;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BankingOperationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void bankingOperationAddMoneyTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/bankingOperation/bankingOperation_add_money")).andExpect(status().isOk());
		userService.setUserEmailSession("UserWithoutCount@gmail.com");
		mockMvc.perform(get("/bankingOperation/bankingOperation_add_money")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void bankingOperationSendMoneyTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/bankingOperation/bankingOperation_send_money")).andExpect(status().isOk());
		userService.setUserEmailSession("UserWithoutCount@gmail.com");
		mockMvc.perform(get("/bankingOperation/bankingOperation_send_money")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void bankingOperationHistoryTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/bankingOperation/bankingOperation_history")).andExpect(status().isOk());
		userService.setUserEmailSession("UserWithoutCount@gmail.com");
		mockMvc.perform(get("/bankingOperation/bankingOperation_history")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void saveBankingOperationSendMoneyTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		BankingOperationSendMoneyModel bankingOperationSendMoney = new BankingOperationSendMoneyModel();
		mockMvc.perform(post("/bankingOperation/bankingOperation_send_money_send")
				.flashAttr("bankingOperationSendMoney", bankingOperationSendMoney)).andExpect(status().isOk());

		bankingOperationSendMoney.setMoney(9000);
		mockMvc.perform(post("/bankingOperation/bankingOperation_send_money_send")
				.flashAttr("bankingOperationSendMoney", bankingOperationSendMoney)).andExpect(status().isOk());

		bankingOperationSendMoney.setMoney(10);
		bankingOperationSendMoney.setBuddy("lily.cooper@hotmail.fr");
		mockMvc.perform(post("/bankingOperation/bankingOperation_send_money_send")
				.flashAttr("bankingOperationSendMoney", bankingOperationSendMoney)).andExpect(status().isOk());

		bankingOperationSendMoney.setMoney(10);
		bankingOperationSendMoney.setBuddy("a@a");
		mockMvc.perform(post("/bankingOperation/bankingOperation_send_money_send")
				.flashAttr("bankingOperationSendMoney", bankingOperationSendMoney)).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void saveBankingOperationAddMoneyTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		BankingOperationAddMoneyModel bankingOperationAddMoney = new BankingOperationAddMoneyModel();
		mockMvc.perform(post("/bankingOperation/bankingOperation_add_money_add").flashAttr("bankingOperationAddMoney",
				bankingOperationAddMoney)).andExpect(status().is3xxRedirection());
	}

}
