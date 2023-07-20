package com.paymybuddy.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.paymybuddy.service.UserService;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
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

}
