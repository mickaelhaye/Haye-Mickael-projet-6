package com.paymybuddy.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.UserService;

/**
 * userService is the class to manage service for user entity
 * 
 * @author Mickael Hayé
 */
@Service
public class UserServiceImpl<Objet> implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private String UserEmailSession;

	/**
	 * recover the email of the session
	 */
	@Override
	public String getUserEmailSession() {
		return UserEmailSession;
	}

	/**
	 * put the email of the session with the authentification
	 */
	@Override
	public void setUserEmailSession(Authentication authentification) {
		if (OAuth2AuthenticationToken.class.isInstance(authentification)) {
			DefaultOidcUser test = (DefaultOidcUser) authentification.getPrincipal();

			Map<String, Object> mapTest = test.getAttributes();
			if (mapTest.containsKey("email")) {
				UserEmailSession = (String) mapTest.get("email");
			}
		} else {
			UserEmailSession = authentification.getName();
		}
	}

	/**
	 * put the email of the session with an email
	 */
	@Override
	public void setUserEmailSession(String email) {
		UserEmailSession = email;
	}

	/**
	 * recovers all users
	 */
	@Override
	public Iterable<UserModel> getUsers() {
		return userRepository.findAll();
	}

	/**
	 * recover user by id
	 */
	@Override
	public Optional<UserModel> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	/**
	 * recover user by an email
	 */
	@Override
	public Optional<UserModel> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * recover user by email session
	 */
	@Override
	public UserModel getUserByEmail() {
		Optional<UserModel> optUser = userRepository.findByEmail(getUserEmailSession());
		UserModel user = optUser.get();
		return user;
	}

	/**
	 * delete user by email
	 */
	@Override
	public void delUserByEmail(String email) {
		Optional<UserModel> optUser = userRepository.findByEmail(email);
		UserModel user = optUser.get();
		List<UserModel> usersList = (List<UserModel>) getUsers();

		// Remove user from other users' buddy list

		for (UserModel userTest : usersList) {
			for (UserModel userBuddyTest : userTest.getUsers()) {
				if (user.getEmail().equals(userBuddyTest.getEmail())) {
					userTest.removeBuddyToUserList(userBuddyTest);
					break;
				}
			}
		}

		delUser(user);
	}

	/**
	 * add an user
	 */
	@Override
	public UserModel addUser(UserModel user) throws Exception {
		if (emailExists(user.getEmail())) {
			throw new Exception("There is an account with that email address: " + user.getEmail());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);

	}

	/**
	 * update an user
	 */
	@Override
	public UserModel updateUser(UserModel user) {
		return userRepository.save(user);
	}

	/**
	 * add buddy
	 */
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

		user.setBuddyToUserList(buddy);
		try {
			updateUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * delete buddy
	 */
	@Override
	public void delBuddy(String buddyEmail, String userEmail) {
		Optional<UserModel> OptUser = userRepository.findByEmail(userEmail);
		UserModel user = OptUser.get();

		for (UserModel userTest : user.getUsers()) {
			if (userTest.getEmail().equals(buddyEmail)) {
				user.removeBuddyToUserList(userTest);
				break;
			}
		}
		try {
			updateUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * to know if an user have already this email
	 */
	@Override
	public boolean emailExists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	/**
	 * delete user
	 */
	@Override
	public void delUser(UserModel user) {
		userRepository.delete(user);
	}

	/**
	 * to know if a buddy exit with user objet
	 */
	@Override
	public boolean buddyExists(String buddyEmail, UserModel user) {
		for (UserModel userTest : user.getUsers()) {
			if (userTest.getEmail().equals(buddyEmail)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * to know if a buddy exit with email
	 */
	@Override
	public boolean buddyExists(String buddyEmail, String userEmail) {
		Optional<UserModel> OptUser = userRepository.findByEmail(userEmail);
		UserModel user = OptUser.get();

		for (UserModel userTest : user.getUsers()) {
			if (userTest.getEmail().equals(buddyEmail)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * to know if buddy is already in the list
	 */
	@Override
	public List<UserModel> buddyListfromUser(String userEmail) {
		Optional<UserModel> OptUser = userRepository.findByEmail(userEmail);
		UserModel user = OptUser.get();
		return user.getUsers();
	}

	/**
	 * to update some parameters of an user
	 */
	@Override
	public void updateSomeParameters(UserModel user) {

		UserModel userUpdate = getUserByEmail();

		userUpdate.setName(user.getName());
		userUpdate.setFirstname(user.getFirstname());
		userUpdate.setBirthdate(user.getBirthdate());
		userUpdate.setAddress(user.getAddress());
		updateUser(userUpdate);
	}

	/**
	 * to know if the role of the user is admin
	 */
	@Override
	public boolean getRoleOfUserSessionIsAdmin() {
		UserModel user = getUserByEmail();
		if (user.getRole().equals("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

	/**
	 * to create an user if the the new user is a google user
	 */
	@Override
	public void createUserAuth2(Authentication authentification) {
		if (OAuth2AuthenticationToken.class.isInstance(authentification)) {
			DefaultOidcUser test = (DefaultOidcUser) authentification.getPrincipal();

			Map<String, Object> mapTest = test.getAttributes();
			if (mapTest.containsKey("email")) {
				String email = (String) mapTest.get("email");

				if (!emailExists(email)) {
					UserModel user = new UserModel();
					user.setEmail(email);
					user.setName((String) mapTest.get("family_name"));

					try {
						updateUser(user);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}
	}

	/**
	 * to know if it's a new google user
	 */
	@Override
	public boolean NewuserTestOAuth2(Authentication authentification) {
		if (OAuth2AuthenticationToken.class.isInstance(authentification)) {
			DefaultOidcUser test = (DefaultOidcUser) authentification.getPrincipal();

			Map<String, Object> mapTest = test.getAttributes();
			if (mapTest.containsKey("email")) {
				String email = (String) mapTest.get("email");

				if (!emailExists(email)) {
					return true;
				}
			}

		}
		return false;
	}
}
