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

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.service.UserService;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void userAddAccountTest() throws Exception {
		userService.setUserEmailSession("UserWithoutCount@gmail.com");
		mockMvc.perform(get("/account/account_create")).andExpect(status().isOk());
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/account/account_create")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void accountDelTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/account/account_del")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void accountDeleteTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/account/account_del_account/delete/John.boyd@gmail.com"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(username = "UserWithoutCount@gmail.com")
	@Test
	void saveAccountTest() throws Exception {
		userService.setUserEmailSession("UserWithoutCount@gmail.com");
		AccountModel account = new AccountModel();
		mockMvc.perform(post("/account/accounts").flashAttr("account", account)).andExpect(status().isOk());
		AccountModel account2 = new AccountModel();
		mockMvc.perform(post("/account/accounts").flashAttr("account", account2)).andExpect(status().isOk());
	}

}
