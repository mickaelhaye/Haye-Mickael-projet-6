package com.paymybuddy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BankingOperationSendMoneyModel is the model class with data to sending money
 * 
 * @author Mickael Hayé
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankingOperationSendMoneyModel {

	private float money;
	private float balance;
	private String description;
	private String buddy;
}
