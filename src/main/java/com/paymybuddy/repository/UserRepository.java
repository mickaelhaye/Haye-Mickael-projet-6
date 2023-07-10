package com.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.entity.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer> {

	Optional<UserModel> findByEmail(String email);

}
