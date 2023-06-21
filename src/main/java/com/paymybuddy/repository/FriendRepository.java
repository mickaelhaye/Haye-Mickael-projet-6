package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.FriendModel;

@Repository
public interface FriendRepository extends CrudRepository<FriendModel, Integer> {

}
