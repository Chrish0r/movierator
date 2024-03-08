package com.movierator.movierator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.movierator.movierator.model.RegularUser;

public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
	
	@Query("SELECT ru FROM RegularUser ru WHERE ru.user.id = :userId")
	Optional<RegularUser> findRegularUserByUserId(Long userId); 
}
