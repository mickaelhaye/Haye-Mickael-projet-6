package com.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.dto.HomepageModel;
import com.paymybuddy.model.dto.UserAddBuddyModel;
import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.UserService;

/**
 * UserController is the class to manage web page call for user
 * 
 * @author Mickael Hayé
 */
@Controller
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	/**
	 * manage the call to the first web page
	 * 
	 * @return the web page paymybuddy
	 */
	@GetMapping("/paymybuddy")
	public String start() {
		// logger.debug("start");
		return "/paymybuddy";
	}

	/**
	 * manage the call to the web page to the homepage
	 * 
	 * @param model            = contains data for homepage
	 * @param authentification
	 * @return the web page homepage
	 */
	@GetMapping("/homepage")
	public String homepage(Model model, Authentication authentification) {
		logger.debug("homepage");
		userService.setUserEmailSession(authentification);
		if (userService.NewuserTestOAuth2(authentification)) {
			userService.createUserAuth2(authentification);
			// Create account by default
			UserModel user = userService.getUserByEmail();
			AccountModel account = new AccountModel();
			account.setName("account of " + user.getEmail());
			try {
				accountService.addAccountToUser(account, user.getEmail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		HomepageModel homepage = new HomepageModel();
		homepage.setUserEmailSession("user :" + userService.getUserEmailSession());
		model.addAttribute("homepage", homepage);

		if (userService.getRoleOfUserSessionIsAdmin()) {
			return "/homepageAdmin";
		}
		return "/homepage";
	}

	/**
	 * manage the call to the web page user_create
	 * 
	 * @param model = contains data for the new user
	 * @return the web page user_create
	 */
	@GetMapping("/user/user_create")
	public String userCreate(Model model) {
		logger.debug("userCreate");
		// create user object to hold user form data
		UserModel user = new UserModel();
		model.addAttribute("user", user);
		return "/user/user_create";
	}

	/**
	 * manage the data of the new user
	 * 
	 * @param user = data for the new user
	 * @return the web page user_create_successfull or user_already_exist
	 */
	@PostMapping("/user/users")
	public String saveUser(@ModelAttribute("user") UserModel user) {
		logger.debug("saveUser + user=" + user);
		try {
			userService.addUser(user);
			// Create account by default
			AccountModel account = new AccountModel();
			account.setName("account of " + user.getEmail());
			accountService.addAccountToUser(account, user.getEmail());

		} catch (Exception e) {

			e.printStackTrace();
			return "/user/user_already_exist";
		}

		return "/user/user_create_successfull";

	}

	/**
	 * manage the call to the web page user_add_buddy
	 * 
	 * @param model = contains data to add a buddy
	 * @return the web page user_add_buddy
	 */
	@GetMapping("/user/user_add_buddy")
	public String userAddBuddy(Model model) {
		logger.debug("userAddBuddy");
		// add buddy
		UserAddBuddyModel userAddBuddy = new UserAddBuddyModel();
		model.addAttribute("userAddBuddy", userAddBuddy);
		return "/user/user_add_buddy";
	}

	/**
	 * manage the data of the new buddy
	 * 
	 * @param userAddBuddy = data for the new buddy
	 * @return the web page user_buddy_add_successfull or user_buddy_add_successfull
	 *         or user_buddy_no_account or user_bad_buddy
	 */

	@PostMapping("/user/buddys")
	public String saveBuddy(@ModelAttribute("recupValue") UserAddBuddyModel userAddBuddy) {
		logger.debug("saveBuddy userAddBuddy=" + userAddBuddy);
		if (!userService.emailExists(userAddBuddy.getBudddyEmail())) {
			return "user/user_buddy_no_exist";
		}

		if (!accountService.userHaveAccount(userAddBuddy.getBudddyEmail())) {
			return "user/user_buddy_no_account";
		}

		try {
			userService.addBuddy(userAddBuddy.getBudddyEmail(), userService.getUserEmailSession());
		} catch (Exception e) {

			e.printStackTrace();
			return "/user/user_bad_buddy";
		}
		return "/user/user_buddy_add_successfull";
	}

	/**
	 * manage the call to the web page to del a buddy
	 * 
	 * @param model = buddy list of the user
	 * @return the web page buddy del
	 */
	@GetMapping("/user/user_del_buddy")
	public String userDelBuddy(Model model) {
		model.addAttribute("buddys", userService.buddyListfromUser(userService.getUserEmailSession()));
		logger.debug("userDelBuddy");
		return "/user/user_del_buddy";
	}

	/**
	 * manage the call to the web page to send the buddy deleting
	 * 
	 * @param email = email of the buddy to be deleted
	 * @return the web page user_del_buddy
	 */
	@GetMapping("/user/user_del_buddy/delete/{email}")
	public String userDelBuddyDelete(@PathVariable String email) {
		logger.debug("userDelBuddyDelete email=" + email);
		userService.delBuddy(email, userService.getUserEmailSession());
		return "redirect:/user/user_del_buddy";
	}

	/**
	 * manage the call to the web page to update an user
	 * 
	 * @param model = data of an user
	 * @return the web page user_update
	 */
	@GetMapping("/user/user_update")
	public String userUpdate(Model model) {
		logger.debug("userUpdate");
		// create user object to hold user form data
		UserModel user = userService.getUserByEmail();
		model.addAttribute("user", user);
		return "/user/user_update";
	}

	/**
	 * manage the call to the web page to update an user
	 * 
	 * @param user = data of an user update
	 * @return the web page user_update_successfull
	 */
	@PostMapping("/user/user_update_update")
	public String userUpdateUpdate(@ModelAttribute("user") UserModel user) {
		logger.debug("userUpdateUpdate user=" + user);
		userService.updateSomeParameters(user);
		return "/user/user_update_successfull";
	}

	/**
	 * manage the call to the web page to delete an user
	 * 
	 * @param model = List of all users
	 * @return the web page user_del_user
	 */
	@GetMapping("/user/admin/user_del_user")
	public String userDelUser(Model model) {
		logger.debug("userDelUser");
		model.addAttribute("users", userService.getUsers());
		return "/user/admin/user_del_user";
	}

	/**
	 * manage the call to the web page to send a new account
	 * 
	 * @param email = user to be deleted
	 * @return the web page user_del_user
	 */
	@GetMapping("/user/admin/user_del_user/delete/{email}")
	public String userDelUserDelete(@PathVariable String email) {
		logger.debug("userDelUserDelete email=" + email);
		userService.delUserByEmail(email);
		return "redirect:/user/admin/user_del_user";
	}

}
