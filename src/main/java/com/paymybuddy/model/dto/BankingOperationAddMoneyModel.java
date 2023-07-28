package com.paymybuddy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BankingOperationAddMoneyModel is the model class with data to adding money
 * 
 * @author Mickael Hayé
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankingOperationAddMoneyModel {

	private float money;
	private float balance;
	private String description;
}
