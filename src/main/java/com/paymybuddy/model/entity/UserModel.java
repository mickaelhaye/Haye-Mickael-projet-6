package com.paymybuddy.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "user")
@Transactional
public class UserModel implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "name")
	private String name;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "email")
	private String email;

	@Column(name = "birthdate")
	private String birthdate;

	@Column(name = "address")
	private String address;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role = "USER";

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "user2_id"))
	private List<UserModel> users = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "userId2")
	private List<AccountModel> accounts = new ArrayList<>();

	public void setAccountToList(AccountModel account) {
		this.accounts.add(account);
	}

	public void setBuddyToUserList(UserModel user) {
		this.users.add(user);
	}

	public void removeBuddyToUserList(UserModel user) {
		this.users.remove(user);
	}

	public void removeAccountToAccountList(AccountModel account) {
		this.accounts.remove(account);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
