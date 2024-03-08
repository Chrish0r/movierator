package com.movierator.movierator.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.movierator.movierator.jokesApi.JokesApiClient;
import com.movierator.movierator.jokesApi.RandomJoke;
import com.movierator.movierator.model.Admin;
import com.movierator.movierator.model.Moderator;
import com.movierator.movierator.model.NewsletterSubscriber;
import com.movierator.movierator.model.RegularUser;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.AdminRepository;
import com.movierator.movierator.repository.ModeratorRepository;
import com.movierator.movierator.repository.RegularUserRepository;
import com.movierator.movierator.repository.UserRepository;

@Controller
public class HomeController {

	private static final String ADMIN_SESSION = "adminSession";
	private static final String MODERATOR_SESSION = "moderatorSession";
	private static final String REGULAR_USER_SESSION = "regularUserSession";
	
	private static final String ADMIN_ROLE = "ADMIN";
	private static final String MODERATOR_ROLE = "MODERATOR";
	private static final String REGULAR_USER_ROLE = "REGULAR_USER";
	
	

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	ModeratorRepository moderatorRepository;

	@Autowired
	RegularUserRepository regularUserRepository;
	
	@Autowired
	JokesApiClient jokesClient;
	
	@GetMapping("/")
	public String home(@ModelAttribute("newsletterSubscriber") NewsletterSubscriber newsletterSubscriber,
			HttpServletRequest request, Principal principal, Model model) {

		RandomJoke randomJoke = jokesClient.getRandomJoke();
		
		model.addAttribute("randomJoke", randomJoke);
		
		if (principal == null) {
			return "index";
		}

		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		String myAuthorities = authorities.toString();

		logger.info("Authorities of the logged user " + principal.getName() + ": " + myAuthorities);

		// searching in the database for the underlying user
		Optional<User> loggedUserOpt = userRepository.findUserByLogin(principal.getName());

		if (myAuthorities.contains(ADMIN_ROLE)) {

			Optional<Admin> adminOpt;
	
			adminOpt = adminRepository.findAdminByUserId(loggedUserOpt.get().getId());
			request.getSession().setAttribute(ADMIN_SESSION, adminOpt.get());

		}
		if (myAuthorities.contains(MODERATOR_ROLE)) {
			Optional<Moderator> moderatorOpt;

			moderatorOpt = moderatorRepository.findModeratorByUserId(loggedUserOpt.get().getId());
			request.getSession().setAttribute(MODERATOR_SESSION, moderatorOpt.get());

		}
		if (myAuthorities.contains(REGULAR_USER_ROLE)) {
			Optional<RegularUser> regularUserOpt;

			regularUserOpt = regularUserRepository.findRegularUserByUserId(loggedUserOpt.get().getId());
			request.getSession().setAttribute(REGULAR_USER_SESSION, regularUserOpt.get());

		}
		
		return "index";
	}
	
	@GetMapping("/work-in-progress")
	public String showWorkInProgressPage() {

		return "work-in-progress";
	}
}
