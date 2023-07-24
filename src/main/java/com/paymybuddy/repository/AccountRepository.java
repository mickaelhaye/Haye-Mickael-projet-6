package com.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.entity.AccountModel;

/**
 * AccountRepository is an interface which allows to exchange with database for
 * the entity account
 * 
 * @author Mickael Hayé
 */
@Repository
public interface AccountRepository extends CrudRepository<AccountModel, Integer> {

	@Query(value = "SELECT * FROM account WHERE (account.user_id2= :user_id AND account.account_name= :name )", nativeQuery = true)
	public Optional<AccountModel> findByUserIdAndName(@Param("user_id") Integer userId, @Param("name") String name);

	@Query(value = "SELECT * FROM account JOIN user ON (user.user_id = account.user_id2) WHERE (user.email = :email AND account.account_name = :accountname)", nativeQuery = true)
	public Optional<AccountModel> findByEmailAndAccountName(@Param("email") String email,
			@Param("accountname") String accountName);
}
