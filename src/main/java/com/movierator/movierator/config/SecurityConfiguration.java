package com.movierator.movierator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.movierator.movierator.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	MyUserDetailsService userDetailsService;
		    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests()
				.antMatchers("/**").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/user/**").permitAll()
				.antMatchers("/login").permitAll()
				
				.anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/login")
				.and()
				.logout()
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)

				.permitAll();

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer ignoreResources() {
		return (webSecurity) -> webSecurity.ignoring().antMatchers("/images/**", "/js/**", "/css/**", "/webjars/**");
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
