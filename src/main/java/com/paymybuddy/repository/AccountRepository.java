package com.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.entity.AccountModel;

@Repository
public interface AccountRepository extends CrudRepository<AccountModel, Integer> {

	@Query(value = "SELECT * FROM `account`  WHERE (`account`.`user_id`= :user_id AND `account`.`name`= :name )", nativeQuery = true)
	public Optional<AccountModel> findByUserIdAndName(@Param("user_id") Integer userId, @Param("name") String name);

}
