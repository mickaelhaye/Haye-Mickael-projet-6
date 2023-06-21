package com.paymybuddy.model;

import jakarta.persistence.Column;

public abstract class PersonModel {

	@Column(name = "name")
	private String name;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "email")
	private String email;
}
