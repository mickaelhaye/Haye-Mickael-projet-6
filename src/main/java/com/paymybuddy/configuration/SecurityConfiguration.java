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
                                .requestMatchers("/account/account_create").authenticated()
                                .requestMatchers("/accounts").authenticated()
                                .requestMatchers("/account/account_del").authenticated()
                                .requestMatchers("/account/account_del_account/delete/{name}").authenticated()
                                .requestMatchers("/bankingOperation/bankingOperation_add_money").authenticated()
                                .requestMatchers("/bankingOperation_add_money").authenticated()
                                .requestMatchers("/bankingOperation/bankingOperation_send_money").authenticated()
                                .requestMatchers("/bankingOperation_send_money").authenticated()
                                .requestMatchers("/bankingOperation/bankingOperation_history").authenticated()
                                .requestMatchers("/bankingOperation/bankingOperation_history/page/{pageNo}").authenticated()
                                .requestMatchers("/user/user_add_buddy").authenticated()
                                .requestMatchers("/user/user_del_buddy").authenticated()
                                .requestMatchers("/user/user_del_buddy/delete/{email}").authenticated()
                                .requestMatchers("/buddys").authenticated()
                                .requestMatchers("/account").permitAll()
                                .requestMatchers("/user").permitAll()
                                .requestMatchers("/bankingOperation").permitAll()
                )
                .oauth2Login(withDefaults())
                .formLogin(withDefaults())
                .rememberMe(withDefaults())
                .logout((logout) ->
 				logout.deleteCookies("remove").logoutSuccessUrl("/paymybuddy"));
 					
                
		return http.build();
		//@formatter:on

	}

}
