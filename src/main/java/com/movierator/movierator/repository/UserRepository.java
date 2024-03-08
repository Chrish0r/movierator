package com.movierator.movierator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.movierator.movierator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.active = 1")
	List<User> findAllActive();

	/**
	 * 
	 * @return list of all existing emails, including emails of inactive accounts,
	 *         because those might be potentially restored at some point in the
	 *         future.
	 */
	@Query("SELECT u.email FROM User u")
	List<String> findAllExistingEmails();

	Optional<User> findUserByLogin(String login);

}
