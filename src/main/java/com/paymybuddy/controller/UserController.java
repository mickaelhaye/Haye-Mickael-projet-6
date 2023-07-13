package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.entity.RecupValueModel;
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.AccountService;
import com.paymybuddy.service.UserService;

@Controller
public class UserController {

	private String UserEmailSession;

	@Bean
	public String getUserEmailSession() {
		return UserEmailSession;
	}

	public void setUserEmailSession(String userEmailSession) {
		UserEmailSession = userEmailSession;
	}

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
		setUserEmailSession(authentification.getName());
		RecupValueModel recupValue = new RecupValueModel();
		recupValue.setStringValue1("user :" + this.UserEmailSession);
		model.addAttribute("recupValue", recupValue);
		return "/homepage";
	}

	@GetMapping("/user/user_create")
	public String userCreate(Model model) {

		// create user object to hold user form data
		UserModel user = new UserModel();
		model.addAttribute("user", user);
		return "/user/user_create";
	}

	@PostMapping("/users")
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
	public String userAddBody(Model model) {
		// add buddy
		RecupValueModel recupValue = new RecupValueModel();
		model.addAttribute("recupValue", recupValue);
		return "/user/user_add_buddy";
	}

	@PostMapping("/buddys")
	public String saveUser(@ModelAttribute("recupValue") RecupValueModel recupValue) {

		if (!userService.emailExists(recupValue.getStringValue1())) {
			return "user/user_buddy_no_exist";
		}

		if (!accountService.userHaveAccount(recupValue.getStringValue1())) {
			return "user/user_buddy_no_account";
		}

		try {
			userService.addBuddy(recupValue.getStringValue1(), this.UserEmailSession);
		} catch (Exception e) {

			e.printStackTrace();
			return "/user/user_bad_buddy";
		}
		return "/user/user_buddy_add_successfull";
	}

	@GetMapping("/user/user_del_buddy")
	public String userDelBuddy(Model model) {
		model.addAttribute("buddys", userService.buddyListfromUser(this.UserEmailSession));
		return "/user/user_del_buddy";
	}

	@GetMapping("/user/user_del_buddy/delete/{email}")
	public String userDelBuddyDelete(@PathVariable String email) {
		userService.delBuddy(email, this.UserEmailSession);
		return "redirect:/user/user_del_buddy";
	}

}
