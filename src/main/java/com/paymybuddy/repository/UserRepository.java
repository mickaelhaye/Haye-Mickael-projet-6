package com.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.paymybuddy.model.entity.UserModel;

/**
 * UserRepository is an interface which allows to exchange with database for the
 * entity User
 * 
 * @author Mickael Hayé
 */
public interface UserRepository extends CrudRepository<UserModel, Integer> {

	Optional<UserModel> findByEmail(String email);

}
