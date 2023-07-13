package com.paymybuddy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankingOperationSendMoneyModel {

	private float money;
	private float balance;
	private String description;
	private String buddy;
}
