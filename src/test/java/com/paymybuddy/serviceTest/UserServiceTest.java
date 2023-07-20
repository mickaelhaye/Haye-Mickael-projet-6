package com.paymybuddy.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.UserService;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	void getUsersTest() {
		try {
			List<UserModel> users = (List<UserModel>) userService.getUsers();
			assertEquals("39 rue de bel air, 49300 Cholet", users.get(0).getAddress());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void addAndDelUserTest() {
		List<UserModel> users = (List<UserModel>) userService.getUsers();
		int usersCount = users.size();
		UserModel user = new UserModel();
		user.setEmail("John@gmail.fr");
		user.setPassword("11");
		try {
			userService.addUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<UserModel> usersNew = (List<UserModel>) userService.getUsers();
		int usersNewCount = usersNew.size();
		assertEquals(usersCount + 1, usersNewCount);

		userService.delUser(user);
		usersNew = (List<UserModel>) userService.getUsers();
		usersNewCount = usersNew.size();
		assertEquals(usersCount, usersNewCount);

	}

	@Test
	void addAndDelBuddyTest() {
		List<UserModel> buddyCount = userService.buddyListfromUser("John.boyd@gmail.com");
		int buddysCount = buddyCount.size();
		try {
			userService.addBuddy("UserWithoutCount@gmail.com", "John.boyd@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<UserModel> buddyNewCount = userService.buddyListfromUser("John.boyd@gmail.com");
		int buddysNewCount = buddyNewCount.size();
		assertEquals(buddysCount + 1, buddysNewCount);

		userService.delBuddy("UserWithoutCount@gmail.com", "John.boyd@gmail.com");
		buddysNewCount = buddyNewCount.size();
		assertEquals(buddysCount, buddysNewCount);
	}

	@Test
	void addBuddyEmailNoExistTest() {
		List<UserModel> buddyCount = userService.buddyListfromUser("John.boyd@gmail.com");
		int buddysCount = buddyCount.size();
		try {
			userService.addBuddy("noemail@gmail.com", "John.boyd@gmail.com");
		} catch (Exception e) {
			List<UserModel> buddyNewCount = userService.buddyListfromUser("John.boyd@gmail.com");
			int buddysNewCount = buddyNewCount.size();
			assertEquals(buddysCount, buddysNewCount);
		}
	}

	@Test
	void addBuddyIsYouTest() {
		List<UserModel> buddyCount = userService.buddyListfromUser("John.boyd@gmail.com");
		int buddysCount = buddyCount.size();
		try {
			userService.addBuddy("John.boyd@gmail.com", "John.boyd@gmail.com");
		} catch (Exception e) {
			List<UserModel> buddyNewCount = userService.buddyListfromUser("John.boyd@gmail.com");
			int buddysNewCount = buddyNewCount.size();
			assertEquals(buddysCount, buddysNewCount);
		}
	}

	@Test
	void addBuddyIsAlreadyYourBuddyTest() {
		List<UserModel> buddyCount = userService.buddyListfromUser("John.boyd@gmail.com");
		int buddysCount = buddyCount.size();
		try {
			userService.addBuddy("lily.cooper@hotmail.fr", "John.boyd@gmail.com");
		} catch (Exception e) {
			List<UserModel> buddyNewCount = userService.buddyListfromUser("John.boyd@gmail.com");
			int buddysNewCount = buddyNewCount.size();
			assertEquals(buddysCount, buddysNewCount);
		}
	}

	@Test
	void updateSomeParametersTest() {
		userService.setUserEmailSession("John.boyd@gmail.com");
		Optional<UserModel> optUser = userService.getUserByEmail("John.boyd@gmail.com");
		UserModel user1 = optUser.get();
		String name1 = user1.getName();
		String email1 = user1.getEmail();

		try {
			user1.setName("Robert");
			userService.updateSomeParameters(user1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Optional<UserModel> OptUser2 = userService.getUserByEmail("John.boyd@gmail.com");
		UserModel user2 = OptUser2.get();
		String name2 = user2.getName();
		String email2 = user2.getEmail();

		assertEquals(email1, email2);
		assertNotEquals(name1, name2);
	}

	@Test
	void emailModifyTest() {
		userService.setUserEmailSession("John.boyd@gmail.com");
		UserModel user = new UserModel();
		user.setEmail("John@gmail.fr");
		userService.emailModify(user);
		assertTrue(userService.emailModify(user));
		user.setEmail("John.boyd@gmail.com");
		userService.emailModify(user);
		assertFalse(userService.emailModify(user));

	}

	@Test
	void buddyExistsTest() {
		userService.setUserEmailSession("John.boyd@gmail.com");
		assertTrue(userService.buddyExists("lily.cooper@hotmail.fr", "John.boyd@gmail.com"));
		assertFalse(userService.buddyExists("John@gmail.fr", "John.boyd@gmail.com"));
	}

	@Test
	void delUserByEmailTest() {
		List<UserModel> users = (List<UserModel>) userService.getUsers();
		int usersCount = users.size();

		userService.delUserByEmail("John.boyd@gmail.com");

		List<UserModel> usersnew = (List<UserModel>) userService.getUsers();
		int usersnewCount = usersnew.size();

		assertEquals(usersCount - 1, usersnewCount);

	}

	@Test
	void delUserByIdTest() {
		Optional<UserModel> OptUser = userService.getUserById(1);
		UserModel user = OptUser.get();
		assertEquals("Boyd", user.getName());
	}
}
