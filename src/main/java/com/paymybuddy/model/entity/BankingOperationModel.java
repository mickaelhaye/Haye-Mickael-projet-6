package com.paymybuddy.model.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "banking_operation")
public class BankingOperationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banking_operation_id")
	private int bankingOperationId;

	@Column(name = "date")
	private String date;

	@Column(name = "hour")
	private String hour;

	@Column(name = "amount")
	private int amount;

	@Column(name = "description")
	private String description;

	@Column(name = "type_transaction")
	private String typeTransaction;

}
