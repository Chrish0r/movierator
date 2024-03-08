package com.movierator.movierator.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movierator.movierator.model.Authority;
import com.movierator.movierator.model.MediaRating;
import com.movierator.movierator.model.RegularUser;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.AdminRepository;
import com.movierator.movierator.repository.MediaRatingRepository;
import com.movierator.movierator.repository.ModeratorRepository;
import com.movierator.movierator.repository.RegularUserRepository;
import com.movierator.movierator.repository.UserRepository;
import com.movierator.movierator.util.Constants;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

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

	@GetMapping("/")
	public ResponseEntity<List<User>> findAllActiveUsers() {

		logger.info("passing in UserRestController...");

		List<User> users = new ArrayList<User>();

		try {
			users = userRepository.findAllActive();

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		logger.info("Processing getting user by id with PUT");

		try {
			Optional<User> userOpt = userRepository.findById(id);

			if (userOpt.isPresent()) {
				return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/")
	public ResponseEntity<RegularUser> createRegularUser(@RequestBody User user) {

		logger.info("Processing the add with POST");
		logger.info(user.getLogin());

		RegularUser regularUserDB = new RegularUser();
		User userDB = new User();
		userDB.setPassword((passwordEncoder.encode(user.getPassword())));
		userDB.setLogin(user.getLogin());
		userDB.setEmail(user.getEmail());
		userDB.setActive(1);
		// birthday will be only automatically encrypted when it is stored into the
		// database
		userDB.setBirthday(user.getBirthday());

		regularUserDB.setUser(userDB);

		List<Authority> myAuthorities = new ArrayList<Authority>();
		myAuthorities.add(new Authority(Constants.AUTHORITY_REGULAR_USER));

		userDB.setMyAuthorities(myAuthorities);
		userDB.setActive(1);

		try {
			/*
			 * Persisting the regularUser object into the database will also automatically
			 * persist the user object into the database since regularUser contains user and
			 * CascadeType in the corresponding model class is set to 'ALL'.
			 */
			regularUserDB = regularUserRepository.save(regularUserDB);

			return new ResponseEntity<>(regularUserDB, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateEmailByUserId(@RequestParam String email, @RequestParam long id) {
		logger.info("Processing updating an user email with PUT");

		if (email.isBlank()) {
			throw new IllegalArgumentException("New email may not be empty!");
		}
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		user.setEmail(email);

		try {
			// Updating i.e., persisting user into the database
			userRepository.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {

		logger.info("Processing deleting an user with DELETE");
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setActive(0);
		try {
			// Updating i.e., persisting user into the database
			userRepository.save(user);

			// loading of all ratings assigned to the respective user from the database
			List<MediaRating> mediaRatings = mediaRatingRepository.getMediaRatingsByUser(user);

			// removing all media ratings assigned to the user
			mediaRatingRepository.deleteAll(mediaRatings);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
