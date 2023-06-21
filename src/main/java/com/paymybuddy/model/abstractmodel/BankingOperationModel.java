package com.paymybuddy.model.abstractmodel;

import jakarta.persistence.Column;

public abstract class BankingOperationModel {

	@Column(name = "date")
	private String date;

	@Column(name = "hour")
	private String hour;

	@Column(name = "amount")
	private int amount;

	@Column(name = "description")
	private String description;

}
