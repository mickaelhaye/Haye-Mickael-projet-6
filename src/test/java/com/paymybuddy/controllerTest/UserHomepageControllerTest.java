package com.paymybuddy.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UserHomepageControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void homepageTest() throws Exception {
		mockMvc.perform(get("/homepage")).andExpect(status().isOk());
	}

	@WithMockUser(username = "lily.cooper@hotmail.fr")
	@Test
	void homepage2Test() throws Exception {
		mockMvc.perform(get("/homepage")).andExpect(status().isOk());
	}

}
