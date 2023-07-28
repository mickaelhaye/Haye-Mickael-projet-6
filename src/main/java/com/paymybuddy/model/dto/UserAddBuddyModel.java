package com.paymybuddy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserAddBuddyModel is the model class with data to add a buddy
 * 
 * @author Mickael Hayé
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddBuddyModel {

	private String budddyEmail;
}
