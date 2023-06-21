package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.ExternBankTransferModel;

@Repository
public interface ExternBankTransferRepository extends CrudRepository<ExternBankTransferModel, Integer> {

}
