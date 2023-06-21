package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.CountModel;

@Repository
public interface CountRepository extends CrudRepository<CountModel, Integer> {

}
