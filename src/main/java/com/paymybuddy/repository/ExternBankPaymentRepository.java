package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.ExternBankPaymentModel;

@Repository
public interface ExternBankPaymentRepository extends CrudRepository<ExternBankPaymentModel, Integer> {

}
