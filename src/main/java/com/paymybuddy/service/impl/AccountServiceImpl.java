package com.paymybuddy.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.model.entity.AccountModel;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Iterable<AccountModel> getAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Optional<AccountModel> getAccountById(Integer id) {
		return accountRepository.findById(id);
	}

	@Override
	public AccountModel addAccount(AccountModel account) {
		return accountRepository.save(account);
	}

	@Override
	public void delAccount(AccountModel account) {
		accountRepository.delete(account);

	}

}
