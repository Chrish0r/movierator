package com.movierator.movierator.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.movierator.movierator.model.Authority;
import com.movierator.movierator.model.User;

public class MyUserDetails implements UserDetails {
	
	private static final long serialVersionUID = -2049446841804681717L;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	
	public MyUserDetails(User user) {
		this.userName = user.getLogin();
		this.password = user.getPassword();
		logger.info("password of the user is = " + password);
		logger.info("userName of the user is = " + this.userName);
		logger.info("birthday of the user is = " + user.getBirthday());
		this.active = (user.getActive() > 0 ? true : false);
		this.authorities = new ArrayList<>();
		
		List<Authority> myAuthorities = user.getMyAuthorities();
		logger.info("the user " +  user.getLogin() + " has " + 
				myAuthorities.size() + " authorities");
		
		int count = 0;
		for(Authority myAuthority : myAuthorities) {
			authorities.add(new SimpleGrantedAuthority(myAuthority.getDescription().toUpperCase()));
			
			logger.info("the profile " + count + " of " + user.getLogin() + " is " + myAuthority.getDescription());
			count++;
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
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
		return this.active;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
