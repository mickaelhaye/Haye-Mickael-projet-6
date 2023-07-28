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

import com.paymybuddy.model.dto.UserAddBuddyModel;
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.UserService;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@Test
	void startTest() throws Exception {
		mockMvc.perform(get("/paymybuddy")).andExpect(status().isOk());
	}

	@Test
	void userCreateTest() throws Exception {
		mockMvc.perform(get("/user/user_create")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void saveUserTest() throws Exception {
		UserModel user = new UserModel();
		mockMvc.perform(post("/user/users").flashAttr("user", user)).andExpect(status().isOk());
		user.setEmail("test@gmail.com");
		user.setPassword("11");
		mockMvc.perform(post("/user/users").flashAttr("user", user)).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void userAddBuddyTest() throws Exception {
		mockMvc.perform(get("/user/user_add_buddy")).andExpect(status().isOk());
	}

	@WithMockUser(username = "lily.cooper@hotmail.fr")
	@Test
	void saveBuddyTest() throws Exception {
		UserAddBuddyModel userAddBuddy = new UserAddBuddyModel();
		mockMvc.perform(post("/user/buddys").flashAttr("recupValue", userAddBuddy)).andExpect(status().isOk());
		userAddBuddy.setBudddyEmail("UserWithoutCount@gmail.com");
		mockMvc.perform(post("/user/buddys").flashAttr("recupValue", userAddBuddy)).andExpect(status().isOk());
		userAddBuddy.setBudddyEmail("John.boyd@gmail.com");
		mockMvc.perform(post("/user/buddys").flashAttr("recupValue", userAddBuddy)).andExpect(status().isOk());
		userAddBuddy.setBudddyEmail("d@d");
		mockMvc.perform(post("/user/buddys").flashAttr("recupValue", userAddBuddy)).andExpect(status().isOk());

	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void userDelBuddyTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/user/user_del_buddy")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void userDelBuddyDeleteTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/user/user_del_buddy/delete/lily.cooper@hotmail.fr"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void userUpdateTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/user/user_update")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com", roles = "ADMIN")
	@Test
	void userUpdateUpdate() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		UserModel user = userService.getUserByEmail();
		mockMvc.perform(post("/user/user_update_update").flashAttr("user", user)).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com", roles = "ADMIN")
	@Test
	void userDelUserTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/user/admin/user_del_user")).andExpect(status().isOk());
	}

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void userDelUserDeleteTest() throws Exception {
		userService.setUserEmailSession("John.boyd@gmail.com");
		mockMvc.perform(get("/user/admin/user_del_user/delete/UserWithoutCount@gmail.com"))
				.andExpect(status().is3xxRedirection());
	}

}
