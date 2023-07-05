package com.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.UserService;

@RestController
public class UserControllerOld {
	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public Iterable<UserModel> displayUserList() {
		return userService.getUsers();
	}

	@PostMapping("/user")
	public void addUser(@RequestBody UserModel user) throws Exception {
		userService.addUser(user);
	}

	@PatchMapping("/user")
	public void updateUser(@RequestBody UserModel user) throws Exception {
		userService.addUser(user);
	}

	@DeleteMapping("/user")
	public void deletePerson(@RequestBody UserModel user) {
		userService.delUser(user);
	}

}
