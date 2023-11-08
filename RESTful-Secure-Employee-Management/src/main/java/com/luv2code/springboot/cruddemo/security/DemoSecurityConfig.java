package com.luv2code.springboot.cruddemo.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer.ConcurrencyControlConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {
	
	//add support for jdbc ---no more hardcoded users
	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests( configure ->
		configure
				.requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
				.requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")
				.requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER")
				.requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("MANAGER")
				.requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN")
				);
			http.httpBasic(Customizer.withDefaults());
			
			// disable Cross Site Request Forgery (CSRF)
			//not required for stateless Rest Api's
			http.csrf(csrf -> csrf.disable());
			
			return http.build();
		
	}
	

}
