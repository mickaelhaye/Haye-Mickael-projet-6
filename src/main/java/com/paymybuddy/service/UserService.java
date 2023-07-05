package com.paymybuddy.service;

import java.util.Optional;

import com.paymybuddy.model.entity.UserModel;

public interface UserService {

	public Iterable<UserModel> getUsers();

	public UserModel addUser(UserModel user) throws Exception;

	public Optional<UserModel> getUserById(Integer id);

	public void delUser(UserModel user);

	UserModel getUserByNameAndFirstname(String name, String firstname);

	public boolean emailExists(String email);
}
