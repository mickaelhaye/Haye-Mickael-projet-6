package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.ExternCountModel;

@Repository
public interface ExternCountRepository extends CrudRepository<ExternCountModel, Integer> {

}
