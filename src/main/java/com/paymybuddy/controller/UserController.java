package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/paymybuddy")
	public String start() {
		return "paymybuddy";
	}

	@GetMapping("/users/new")
	public String createUserForm(Model model) {

		// create user object to hold user form data
		UserModel user = new UserModel();
		model.addAttribute("user", user);
		return "create_user";
	}

	@PostMapping("/users")
	public String saveUser(@ModelAttribute("user") UserModel user) {
		try {
			userService.addUser(user);
		} catch (Exception e) {

			e.printStackTrace();
			return "homepage";
		}

		return "redirect:/paymybuddy";

	}
}
