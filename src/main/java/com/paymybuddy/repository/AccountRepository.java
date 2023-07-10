package com.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.entity.AccountModel;

@Repository
public interface AccountRepository extends CrudRepository<AccountModel, Integer> {

	Optional<AccountModel> findByNameAndUserId(String name, int userId);

}
