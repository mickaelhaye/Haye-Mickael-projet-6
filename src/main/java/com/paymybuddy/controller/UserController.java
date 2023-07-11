package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.entity.RecupValueModel;
import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/paymybuddy")
	public String start() {
		return "/paymybuddy";
	}

	// est ce nécessaire?
	@GetMapping("/homepage")
	public String homepage() {
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
	public String saveUser(@ModelAttribute("recupValue") RecupValueModel recupValue, Authentication authentification) {

		try {
			userService.addBuddy(recupValue.getStringValue(), authentification.getName());
		} catch (Exception e) {

			e.printStackTrace();
			return "/user/user_bad_buddy";
		}
		return "/user/user_buddy_add_successfull";
	}

	@GetMapping("/user/user_del_buddy")
	public String userDelBuddy(Model model, Authentication authentification) {
		model.addAttribute("buddys", userService.buddyListfromUser(authentification.getName()));
		return "/user/user_del_buddy";
	}

	@GetMapping("/user/user_del_buddy/delete/{email}")
	public String userDelBuddyDelete(@PathVariable String email, Authentication authentification) {
		userService.delBuddy(email, authentification.getName());
		return "redirect:/user/user_del_buddy";
	}

}
