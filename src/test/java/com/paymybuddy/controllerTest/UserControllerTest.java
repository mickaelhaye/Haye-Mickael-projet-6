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
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void homepageTest() throws Exception {
		mockMvc.perform(get("/homepage")).andExpect(status().isOk());
	}

	@Test
	void startTest() throws Exception {
		mockMvc.perform(get("/paymybuddy")).andExpect(status().isOk());
	}

	@Test
	void userCreateTest() throws Exception {
		mockMvc.perform(get("/user/user_create")).andExpect(status().isOk());
	}

	/*
	 * @WithMockUser(username = "John.boyd@gmail.com")
	 * 
	 * @Test void saveUsereTest() throws Exception { UserModel user = new
	 * UserModel();
	 * mockMvc.perform(post("/user/users").contentType(MediaType.ALL).content(user))
	 * .andExpect(status().isOk()); }
	 */

	@WithMockUser(username = "John.boyd@gmail.com")
	@Test
	void userAddBuddyTest() throws Exception {
		mockMvc.perform(get("/user/user_add_buddy")).andExpect(status().isOk());
	}

	/*
	 * @WithMockUser(username = "John.boyd@gmail.com")
	 * 
	 * @Test void saveUserTest() throws Exception {
	 * mockMvc.perform(post("/user/buddys")).andExpect(status().isOk()); }
	 */

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

	/*
	 * @WithMockUser(username = "John.boyd@gmail.com")
	 * 
	 * @Test void userUpdateUpdate() throws Exception {
	 * userService.setUserEmailSession("John.boyd@gmail.com"); UserModel user =
	 * userService.getUserByEmail();
	 * mockMvc.perform(post("/user/user_update_update")).andExpect(model().
	 * attributeHasErrors("user")); }
	 */

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
