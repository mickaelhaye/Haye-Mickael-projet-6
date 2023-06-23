package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.entity.BankingOperationModel;

@Repository
public interface BankingOperationRepository extends CrudRepository<BankingOperationModel, Integer> {

}
