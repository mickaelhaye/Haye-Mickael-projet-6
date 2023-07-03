package com.paymybuddy.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<UserModel> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<UserModel> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public UserModel getUserByNameAndFirstname(String name, String firstname) {
		Optional<UserModel> optUserModel = userRepository.findByNameAndFirstname(name, firstname);
		UserModel userModelId1 = optUserModel.get();
		return userModelId1;
	}

	@Override
	public UserModel addUser(UserModel user) {
		return userRepository.save(user);
	}

	@Override
	public void delUser(UserModel user) {
		userRepository.delete(user);
	}
}
