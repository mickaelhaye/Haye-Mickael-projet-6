package com.paymybuddy.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//@formatter:off
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/paymybuddy").permitAll()
                                .requestMatchers("/user/user_create").permitAll()
                                .requestMatchers("/users").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/homepage").authenticated()
                                .requestMatchers("/account/user_add_account").authenticated()
                                .requestMatchers("/accounts").authenticated()
                                .requestMatchers("/account").authenticated()
                                .requestMatchers("/user").authenticated()
                                .requestMatchers("/bankingOperation").authenticated()
                )
                .oauth2Login(withDefaults())
                .formLogin(withDefaults());
                
		return http.build();
		//@formatter:on

	}

}
