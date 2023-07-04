package com.paymybuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfiguration {

	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests().requestMatchers("/user").permitAll().and().authorizeHttpRequests()
				.requestMatchers("/paymybuddy").permitAll().and().authorizeHttpRequests().requestMatchers("/users/new")
				.permitAll().and().authorizeHttpRequests().requestMatchers("/users").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/account").authenticated().and().authorizeHttpRequests()
				.requestMatchers("/bankingOperation").authenticated().and().oauth2Login().and().formLogin().and()
				.authenticationProvider(authenticationProvider);

		return http.build();

	}

}
