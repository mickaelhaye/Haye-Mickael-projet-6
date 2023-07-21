package com.paymybuddy.service;

import java.util.List;
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

	public void addBuddy(String buddyEmail, String userEmail) throws Exception;

	public boolean buddyExists(String buddyEmail, UserModel user);

	public List<UserModel> buddyListfromUser(String userEmail);

	public void delBuddy(String buddyEmail, String userEmail);

	public boolean buddyExists(String buddyEmail, String userEmail);

	public String getUserEmailSession();

	public void setUserEmailSession(String userEmailSession);

	public UserModel getUserByEmail();

	public void updateSomeParameters(UserModel user);

	public void delUserByEmail(String email);

	public boolean getRoleOfUserSessionIsAdmin();

}
