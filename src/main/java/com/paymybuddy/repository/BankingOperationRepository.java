package com.paymybuddy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.model.entity.BankingOperationModel;

/**
 * BankingOperationRepository is an interface which allows to exchange with
 * database for the entity BankingOperation
 * 
 * @author Mickael Hayé
 */
public interface BankingOperationRepository extends CrudRepository<BankingOperationModel, Integer> {

	@Query(value = "SELECT * FROM banking_operation JOIN account_banking_operation ON (banking_operation.banking_operation_id = account_banking_operation.banking_operation_id2) JOIN account ON (account.account_id = account_banking_operation.account_id2) JOIN user ON (user.user_id = account.user_id2) WHERE (user.email = :email)", nativeQuery = true)
	public Page<BankingOperationModel> findByEmail(@Param("email") String email, Pageable pageable);

	@Query(value = "SELECT * FROM  banking_operation JOIN account_banking_operation ON (banking_operation.banking_operation_id = account_banking_operation.banking_operation_id2) WHERE banking_operation.banking_operation_id NOT IN (SELECT account_banking_operation.banking_operation_id2 FROM account_banking_operation)", nativeQuery = true)
	public List<BankingOperationModel> BankingOperationOrphan();

}
