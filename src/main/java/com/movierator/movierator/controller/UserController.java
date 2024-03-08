package com.movierator.movierator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.movierator.movierator.model.Authority;
import com.movierator.movierator.model.MediaRating;
import com.movierator.movierator.model.RegularUser;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.AdminRepository;
import com.movierator.movierator.repository.MediaRatingRepository;
import com.movierator.movierator.repository.ModeratorRepository;
import com.movierator.movierator.repository.RegularUserRepository;
import com.movierator.movierator.repository.UserRepository;
import com.movierator.movierator.service.DeletedUserEmailSenderService;
import com.movierator.movierator.util.Constants;

@Controller
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MediaRatingRepository mediaRatingRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	ModeratorRepository moderatorRepository;

	@Autowired
	RegularUserRepository regularUserRepository;
	
	@Autowired
	DeletedUserEmailSenderService deletedUserEmailSenderService;

	@RequestMapping("/user/add")
	public ModelAndView showAddRegularUserForm() {

		ModelAndView mv = new ModelAndView();

		RegularUser regularUser = new RegularUser();
		regularUser.setUser(new User());

		mv.setViewName("/user/user-regular-user-add");
		mv.addObject("regularUserForm", regularUser);

		return mv;
	}

	@RequestMapping("/user/add/process")
	public ModelAndView createUser(@Valid @ModelAttribute("regularUserForm") RegularUser regularUserForm,
			BindingResult bindingResult) {

		ModelAndView mv = new ModelAndView();
		if (bindingResult.hasErrors()) {
			mv.setViewName("/user/user-regular-user-add");
			return mv;
		}

		// ensure uniqueness of user name
		Optional<User> userDB = userRepository.findUserByLogin(regularUserForm.getUser().getLogin());
		if (userDB.isPresent()) {
			logger.warn("Login already exists!");

			bindingResult.rejectValue("user.login", "error.user", "An account already exists for this login.");
			mv.setViewName("/user/user-regular-user-add");
			return mv;
		}

		// ensuring uniqueness of email
		List<String> allExistingEmails = userRepository.findAllExistingEmails();
		if (allExistingEmails != null && !allExistingEmails.isEmpty()) {
			if (allExistingEmails.contains(regularUserForm.getUser().getEmail())) {
				logger.warn("Email already exists!");

				bindingResult.rejectValue("user.email", "error.user", "Email already exists");
				mv.setViewName("/user/user-regular-user-add");
				return mv;
			}
		}

		User user = regularUserForm.getUser();
		user.setPassword((passwordEncoder.encode(regularUserForm.getUser().getPassword())));

		List<Authority> myAuthorities = new ArrayList<Authority>();
		myAuthorities.add(new Authority(Constants.AUTHORITY_REGULAR_USER));

		user.setMyAuthorities(myAuthorities);
		user.setActive(1);


		user = userRepository.save(user);
		regularUserForm.setUser(user);
		regularUserRepository.save(regularUserForm);

		mv.addObject("useradded", "User added!");
		mv.setViewName("user/user-sign-up-success");

		return mv;
	}

	@RequestMapping("/user/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Long userId) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/");

		logger.info("updating the user with the user id " + userId);

		Optional<RegularUser> regularUserOpt;
		regularUserOpt = regularUserRepository.findRegularUserByUserId(userId);
		if (regularUserOpt.isPresent()) {
			mv.setViewName("user/user-regular-user-update");
			mv.addObject("regularUserForm", regularUserOpt.get());
			logger.info("Updating the regular user with the user name " + regularUserOpt.get().getUser().getLogin());
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/user/regular-user/update/process")
	public ModelAndView updateEmailByRegularUser(@Valid @ModelAttribute("regularUserForm") RegularUser regularUserForm,
			BindingResult bindingResult) {

		ModelAndView mv = new ModelAndView();
		User userForm = regularUserForm.getUser();
		// load user from database
		User userDB = userRepository.findById(userForm.getId()).get();
		// before updating email
		String currentEmail = userDB.getEmail();
		
		
		

		// ensuring uniqueness of email
		List<String> allExistingEmails = userRepository.findAllExistingEmails();
		if(allExistingEmails != null && !allExistingEmails.isEmpty()) {
			
			if(currentEmail.equals(regularUserForm.getUser().getEmail())) {
				logger.warn("Already current email!");

				bindingResult.rejectValue("user.email", "error.user", "This is already your current email");
				mv.setViewName("/user/user-regular-user-update");
				return mv;
			}
			
			if(allExistingEmails.contains(regularUserForm.getUser().getEmail())) {
				logger.warn("Email already exists!");

				bindingResult.rejectValue("user.email", "error.user", "Email already exists");
				mv.setViewName("/user/user-regular-user-update");
				return mv;
			}
		}
		
		logger.info("Processing the update of the regular user with the id " + regularUserForm.getId());
		// update email
		userDB.setEmail(userForm.getEmail());

		regularUserForm.setUser(userDB);
		regularUserRepository.save(regularUserForm);

		mv.addObject("user updated", "Regular user updated!");
		mv.setViewName("user/user-regular-user-email-updated-success");
		return mv;
	}

	@RequestMapping("/user/delete/{id}")
	public ModelAndView
	deleteUser(@PathVariable("id") long id, HttpServletRequest request) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		user.setActive(0);
		userRepository.save(user);

		// loading of all ratings assigned to the respective user from the database
		List<MediaRating> mediaRatings = mediaRatingRepository.getMediaRatingsByUser(user);

		// removing all media ratings assigned to the user
		mediaRatingRepository.deleteAll(mediaRatings);

		// terminating current session
		HttpSession session = request.getSession();
		session.invalidate();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/user-account-deleted");
		mv.addObject("user deleted", "User deleted!");
		
		deletedUserEmailSenderService.sendConfirmationMailForDeletedUser(user);
		
		return mv;
	}
}
