package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.FriendlyBankPaymentModel;

@Repository

public interface FriendlyBankPaymentRepository extends CrudRepository<FriendlyBankPaymentModel, Integer> {

}
