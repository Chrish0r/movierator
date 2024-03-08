package com.movierator.movierator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.movierator.movierator.model.Moderator;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long>{
	
	@Query("SELECT m FROM Moderator m WHERE m.user.id = :userId")
	Optional<Moderator> findModeratorByUserId(Long userId); 
	
	

}
