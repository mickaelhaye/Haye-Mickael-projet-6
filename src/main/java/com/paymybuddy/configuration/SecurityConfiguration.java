package com.paymybuddy.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.debug("securityFilterChain");
		//@formatter:off
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                        		.requestMatchers("/images/**").permitAll()
                        		.requestMatchers("/user/admin/*").hasRole("ADMIN")
                        		.requestMatchers("/paymybuddy").permitAll()
                                .requestMatchers("/user/user_create").permitAll()
                                .requestMatchers("/user/users").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/homepage").authenticated()
                                .requestMatchers("/account/*").authenticated()
                                .requestMatchers("/bankingOperation/*").authenticated()
                                .requestMatchers("/user/*").authenticated()
                                .requestMatchers("/account/account_del_account/delete/{name}").authenticated()
                                .requestMatchers("/user/user_del_buddy/delete/{email}").authenticated()
                                .requestMatchers("/user/admin/user_del_user/delete/{email}").authenticated()
                                .requestMatchers("/bankingOperation/bankingOperation_history/page/{pageNo}").authenticated()
                                
                        		
                                
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
