package com.paymybuddy.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.UserService;

import jakarta.transaction.Transactional;

/**
 * userService is the class to manage service for user entity
 * 
 * @author Mickael Hayé
 */
@Service
public class UserServiceImpl<Objet> implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
		logger.debug("getUserEmailSession");
		return UserEmailSession;
	}

	/**
	 * put the email of the session with the authentification
	 */
	@Override
	public void setUserEmailSession(Authentication authentification) {
		logger.debug("setUserEmailSession");
		// test if authentification is in instance of OAuth2
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
		logger.debug("setUserEmailSession email=" + email);
		UserEmailSession = email;
	}

	/**
	 * recovers all users
	 */
	@Override
	@Transactional
	public Iterable<UserModel> getUsers() {
		logger.debug("getUsers");
		return userRepository.findAll();
	}

	/**
	 * recover user by id
	 */
	@Override
	@Transactional
	public Optional<UserModel> getUserById(Integer id) {
		logger.debug("getUserById id=" + id);
		return userRepository.findById(id);
	}

	/**
	 * recover user by an email
	 */
	@Override
	@Transactional
	public Optional<UserModel> getUserByEmail(String email) {
		logger.debug("getUserByEmail email=" + email);
		return userRepository.findByEmail(email);
	}

	/**
	 * recover user by email session
	 */
	@Override
	@Transactional
	public UserModel getUserByEmail() {
		logger.debug("getUserByEmail");
		Optional<UserModel> optUser = userRepository.findByEmail(getUserEmailSession());
		UserModel user = optUser.get();
		return user;
	}

	/**
	 * delete user by email
	 */
	@Override
	@Transactional
	public void delUserByEmail(String email) {
		logger.debug("delUserByEmail email=" + email);
		if (!getUserEmailSession().equals(email)) {
			Optional<UserModel> optUser = userRepository.findByEmail(email);
			UserModel user = optUser.get();
			List<UserModel> usersList = (List<UserModel>) getUsers();
			// Remove user from other users buddy list
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
	}

	/**
	 * add an user
	 */
	@Override
	@Transactional
	public UserModel addUser(UserModel user) throws Exception {
		logger.debug("addUser user=" + user);
		// test if the email is already using
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
	@Transactional
	public UserModel updateUser(UserModel user) {
		logger.debug("updateUser user=" + user);
		return userRepository.save(user);
	}

	/**
	 * add buddy
	 */
	@Override
	@Transactional
	public void addBuddy(String buddyEmail, String userEmail) throws Exception {
		logger.debug("addBuddy buddyEmail=" + buddyEmail + " userEmail=" + userEmail);
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
		// add the buddy to the list
		user.setBuddyToUserList(buddy);
		try {
			updateUser(user);
		} catch (Exception e) {
			logger.error("update user error" + e);
			e.printStackTrace();
		}
	}

	/**
	 * delete buddy
	 */
	@Override
	@Transactional
	public void delBuddy(String buddyEmail, String userEmail) {
		logger.debug("delBuddy buddyEmail=" + buddyEmail + " userEmail=" + userEmail);
		Optional<UserModel> OptUser = userRepository.findByEmail(userEmail);
		UserModel user = OptUser.get();
		// find the buddy for deleting it
		for (UserModel userTest : user.getUsers()) {
			if (userTest.getEmail().equals(buddyEmail)) {
				user.removeBuddyToUserList(userTest);
				break;
			}
		}
		try {
			updateUser(user);
		} catch (Exception e) {
			logger.error("update user error" + e);
			e.printStackTrace();
		}
	}

	/**
	 * to know if an user have already this email
	 */
	@Override
	@Transactional
	public boolean emailExists(String email) {
		logger.debug("emailExists email=" + email);
		return userRepository.findByEmail(email).isPresent();
	}

	/**
	 * delete user
	 */
	@Override
	@Transactional
	public void delUser(UserModel user) {
		logger.debug("delUser user=" + user);
		userRepository.delete(user);
	}

	/**
	 * to know if a buddy exit with user objet
	 */
	@Override
	public boolean buddyExists(String buddyEmail, UserModel user) {
		logger.debug("buddyExists buddyEmail=" + buddyEmail + " user=" + user);
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
	@Transactional
	public boolean buddyExists(String buddyEmail, String userEmail) {
		logger.debug("buddyExists buddyEmail=" + buddyEmail + " userEmail=" + userEmail);
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
	@Transactional
	public List<UserModel> buddyListfromUser(String userEmail) {
		logger.debug("buddyListfromUser userEmail=" + userEmail);
		Optional<UserModel> OptUser = userRepository.findByEmail(userEmail);
		UserModel user = OptUser.get();
		return user.getUsers();
	}

	/**
	 * to update some parameters of an user
	 */
	@Override
	public void updateSomeParameters(UserModel user) {
		logger.debug("updateSomeParameters user=" + user);
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
		logger.debug("getRoleOfUserSessionIsAdmin");
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
		logger.debug("createUserAuth2");
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
						e.printStackTrace();
						logger.error("update user error" + e);
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
		logger.debug("NewuserTestOAuth2");
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
