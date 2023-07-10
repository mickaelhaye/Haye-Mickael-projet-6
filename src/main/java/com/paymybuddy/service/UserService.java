package com.paymybuddy.service;

import java.util.Optional;

import com.paymybuddy.model.entity.UserModel;

public interface UserService {

	public Iterable<UserModel> getUsers();

	public UserModel addUser(UserModel user) throws Exception;

	public Optional<UserModel> getUserById(Integer id);

	public void delUser(UserModel user);

	public boolean emailExists(String email);

	public Optional<UserModel> getUserByEmail(String email);

	public UserModel updateUser(UserModel user);

	public void addBuddy(String emailBuddy) throws Exception;

	public void addBuddy(String buddyEmail, String userEmail) throws Exception;
}
