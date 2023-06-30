package com.paymybuddy.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.paymybuddy.model.entity.UserModel;
import com.paymybuddy.service.UserService;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {

	@Autowired
	private UserService userService;

	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsService(PasswordEncoder
	 * encoder) { List<UserDetails> userDetails = new ArrayList<UserDetails>(); for
	 * (UserModel userModel : userService.getUsers()) {
	 * userDetails.add(User.withUsername(userModel.getEmail()).password(encoder.
	 * encode(userModel.getPassword())) .roles("USER").build()); } return new
	 * InMemoryUserDetailsManager(userDetails); }
	 * 
	 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
	 * Exception { http.csrf(withDefaults()).authorizeRequests(requests ->
	 * requests.requestMatchers("/admin/**").hasRole("ADMIN")
	 * .requestMatchers("/anonymous*").anonymous().requestMatchers("/login*").
	 * permitAll().anyRequest() .authenticated().and() .formLogin(login ->
	 * login.loginPage("/login.html").loginProcessingUrl("/perform_login")
	 * .defaultSuccessUrl("/homepage.html",
	 * true).failureUrl("/login.html?error=true")
	 * .failureHandler(authenticationFailureHandler()).and().logout().logoutUrl(
	 * "/perform_logout")
	 * .deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler())));
	 * return http.build(); }
	 * 
	 * @Bean public PasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 */
	/*
	 * @Bean public SecurityFilterChain filterChain(HttpSecurity httpSecurity)
	 * throws Exception {
	 * httpSecurity.csrf().disable().authorizeHttpRequests().requestMatchers("/user"
	 * ).permitAll()
	 * .requestMatchers("/account").permitAll().anyRequest().authenticated().and().
	 * httpBasic(); return httpSecurity.build();
	 * 
	 * }
	 */

	@Bean
	public UserDetailsService userDetailsService() {

		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		for (UserModel userModel : userService.getUsers()) {
			userDetails.add(User.withUsername(userModel.getEmail())
					.password(passwordEncoder().encode(userModel.getPassword())).roles("USER").build());
		}
		return new InMemoryUserDetailsManager(userDetails);

	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/user").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/paymybuddy").permitAll().and().authorizeHttpRequests()
				.requestMatchers("/users/new").permitAll().and().authorizeHttpRequests().requestMatchers("/users")
				.permitAll().and().authorizeHttpRequests().requestMatchers("/account").authenticated().and()
				.authorizeHttpRequests().requestMatchers("/bankingOperation").authenticated().and().oauth2Login().and()
				.formLogin().and().build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
