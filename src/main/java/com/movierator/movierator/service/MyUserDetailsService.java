package com.movierator.movierator.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movierator.movierator.config.MyUserDetails;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findUserByLogin(username);
		userOpt.orElseThrow(() -> new UsernameNotFoundException("Not found " + username));
		logger.info("User found at the " + UserDetailsService.class.getSimpleName() + " = "+ userOpt.get().getLogin());
		
		return new MyUserDetails(userOpt.get());
	}

}
