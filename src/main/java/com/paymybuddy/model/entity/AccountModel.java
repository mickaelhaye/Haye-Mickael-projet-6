package com.paymybuddy.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * accountModel is the class for the entity account
 * 
 * @author Mickael Hayé
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "account")
@Transactional
public class AccountModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private int accountId;

	@Column(name = "account_name")
	private String name;

	@Column(name = "balance")
	private float balance;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "account_banking_operation", joinColumns = @JoinColumn(name = "account_id2"), inverseJoinColumns = @JoinColumn(name = "banking_operation_id2"))
	private List<BankingOperationModel> bankingOperations = new ArrayList<>();

	public void setBankingOperationsToList(BankingOperationModel bankingOperation) {
		this.bankingOperations.add(bankingOperation);
	}
}
