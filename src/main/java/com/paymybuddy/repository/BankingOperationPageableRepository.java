package com.paymybuddy.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.entity.BankingOperationModel;

@Repository
public interface BankingOperationPageableRepository extends PagingAndSortingRepository<BankingOperationModel, Integer> {

}
