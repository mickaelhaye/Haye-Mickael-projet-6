package com.paymybuddy.controller;

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
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/paymybuddy")
	public String start() {
		return "/paymybuddy";
	}

	// est ce nécessaire?
	@GetMapping("/homepage")
	public String homepage(Model model, Authentication authentification) {
		userService.setUserEmailSession(authentification.getName());
		HomepageModel homepage = new HomepageModel();
		homepage.setUserEmailSession("user :" + userService.getUserEmailSession());
		model.addAttribute("homepage", homepage);

		if (userService.getRoleOfUserSessionIsAdmin()) {
			return "/homepageAdmin";
		}
		return "/homepage";
	}

	@GetMapping("/user/user_create")
	public String userCreate(Model model) {

		// create user object to hold user form data
		UserModel user = new UserModel();
		model.addAttribute("user", user);
		return "/user/user_create";
	}

	@PostMapping("/user/users")
	public String saveUser(@ModelAttribute("user") UserModel user) {
		try {
			userService.addUser(user);
		} catch (Exception e) {

			e.printStackTrace();
			return "/user/user_already_exist";
		}

		return "/user/user_create_successfull";

	}

	@GetMapping("/user/user_add_buddy")
	public String userAddBuddy(Model model) {
		// add buddy
		UserAddBuddyModel userAddBuddy = new UserAddBuddyModel();
		model.addAttribute("userAddBuddy", userAddBuddy);
		return "/user/user_add_buddy";
	}

	@PostMapping("/user/buddys")
	public String saveUser(@ModelAttribute("recupValue") UserAddBuddyModel userAddBuddy) {

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

	@GetMapping("/user/user_del_buddy")
	public String userDelBuddy(Model model) {
		model.addAttribute("buddys", userService.buddyListfromUser(userService.getUserEmailSession()));
		return "/user/user_del_buddy";
	}

	@GetMapping("/user/user_del_buddy/delete/{email}")
	public String userDelBuddyDelete(@PathVariable String email) {
		userService.delBuddy(email, userService.getUserEmailSession());
		return "redirect:/user/user_del_buddy";
	}

	@GetMapping("/user/user_update")
	public String userUpdate(Model model) {

		// create user object to hold user form data
		UserModel user = userService.getUserByEmail();
		model.addAttribute("user", user);
		return "/user/user_update";
	}

	@PostMapping("/user/user_update_update")
	public String userUpdateUpdate(@ModelAttribute("user") UserModel user) {

		try {
			userService.updateSomeParameters(user);
		} catch (

		Exception e) {
			e.printStackTrace();
			return "/user/user_already_exist_update";
		}
		return "/user/user_update_successfull";
	}

	@GetMapping("/user/admin/user_del_user")
	public String userDelUser(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "/user/admin/user_del_user";
	}

	@GetMapping("/user/admin/user_del_user/delete/{email}")
	public String userDelUserDelete(@PathVariable String email) {
		userService.delUserByEmail(email);
		return "redirect:/user/admin/user_del_user";
	}

}
