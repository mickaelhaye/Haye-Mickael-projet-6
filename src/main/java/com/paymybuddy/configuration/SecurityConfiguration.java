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
                        .requestMatchers("/user/admin/*").authenticated()
                        		.requestMatchers("/paymybuddy").permitAll()
                                .requestMatchers("/user/user_create").permitAll()
                                .requestMatchers("/user/users").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/homepage").authenticated()
                                .requestMatchers("/account/*").authenticated()
                                .requestMatchers("/bankingOperation/*").authenticated()
                                .requestMatchers("/user/*").authenticated()
                                .requestMatchers("/user/user_del_buddy/delete/{email}").authenticated()
                                .requestMatchers("/user/admin/user_del_user/delete/{email}").authenticated()
                                
                        		
                                
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
