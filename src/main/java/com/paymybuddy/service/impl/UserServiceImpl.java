package com.paymybuddy.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Iterable<UserModel> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<UserModel> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<UserModel> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserModel addUser(UserModel user) throws Exception {
		if (emailExists(user.getEmail())) {
			throw new Exception("There is an account with that email address: " + user.getEmail());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public UserModel updateUser(UserModel user) {
		return userRepository.save(user);
	}

	@Override
	public void addBuddy(String buddyEmail, String userEmail) throws Exception {

		if (!emailExists(buddyEmail)) {
			throw new Exception("This user doesn't exit: " + buddyEmail);
		}

		if (buddyEmail.equals(userEmail)) {
			throw new Exception("you are this user");
		}

		Optional<UserModel> OptUser = userRepository.findByEmail(userEmail);
		UserModel user = OptUser.get();

		if (buddyExists(buddyEmail, user)) {
			throw new Exception("This email is already your buddy");
		}

		Optional<UserModel> OptBuddy = userRepository.findByEmail(buddyEmail);
		UserModel buddy = OptBuddy.get();

		user.setBoddyToUserList(buddy);
		try {
			updateUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean emailExists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	@Override
	public void delUser(UserModel user) {
		userRepository.delete(user);
	}

	@Override
	public void addBuddy(String emailBuddy) throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean buddyExists(String buddyEmail, UserModel user) {
		for (UserModel userTest : user.getUsers()) {
			if (userTest.getEmail().equals(buddyEmail)) {
				return true;
			}
		}
		return false;
	}

}
