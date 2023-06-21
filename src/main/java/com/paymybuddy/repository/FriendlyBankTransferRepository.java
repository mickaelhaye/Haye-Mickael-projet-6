package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.FriendlyBankTransferModel;

@Repository

public interface FriendlyBankTransferRepository extends CrudRepository<FriendlyBankTransferModel, Integer> {

}
