package com.paymybuddy.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;

import com.paymybuddy.model.entity.UserModel;

@SpringBootTest
class UserModelTest {

	@Test
	void getAuthoritiesTest() {
		UserModel user = new UserModel();
		user.setEmail("test");
		Collection<? extends GrantedAuthority> listcollection = user.getAuthorities();
		assertEquals(1, listcollection.size());
		assertEquals("test", user.getUsername());
		assertTrue(user.isAccountNonExpired());
		assertTrue(user.isAccountNonLocked());
		assertTrue(user.isCredentialsNonExpired());
		assertTrue(user.isEnabled());
	}

}
