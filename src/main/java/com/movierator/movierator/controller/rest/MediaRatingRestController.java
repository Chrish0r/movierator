package com.movierator.movierator.controller.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movierator.movierator.model.MediaRating;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.MediaRatingRepository;
import com.movierator.movierator.repository.UserRepository;

@RestController
@RequestMapping("/api/reviews")
public class MediaRatingRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository userRepository;

	@Autowired
	MediaRatingRepository mediaRatingRepository;

	/**
	 * 
	 * @param id user id of the user being searched for
	 * 
	 * @return HTTP response showing the ten most recent ratings from the relevant
	 *         user in JSON format
	 */
	@GetMapping("/{id}")
	public ResponseEntity<List<MediaRating>> getMediaRatingsByUserId(@PathVariable("id") long id) {

		// limited to the ten latest reviews
		int limit = 10;
		Pageable pageable = PageRequest.of(0, limit, Sort.by("lastModifiedAt").descending());

		try {
			Optional<User> userOpt = userRepository.findById(id);

			// limited to ten latest reviews
			List<MediaRating> foundMediaRatings = mediaRatingRepository.getMediaRatingsByUserLimitedTo(userOpt.get(),
					pageable);
			logger.info("The user with the user id " + id + " has published " + foundMediaRatings.size() + " reviews");

			if (!userOpt.isPresent() || userOpt.get().getActive() != 1) {
				logger.warn("The user with the user id " + id + " does not exist!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			if ((foundMediaRatings == null || foundMediaRatings.isEmpty())) {
				logger.warn("The user with the user id " + id + " does not have any reviews yet");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(foundMediaRatings, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/media/{id}")
	public ResponseEntity<List<MediaRating>> getMediaRatingsByMediaId(@PathVariable("mediaId") long mediaId) {

		int limit = 10;
		Pageable pageable = PageRequest.of(0, limit, Sort.by("lastModifiedAt").descending());

		try {
			List<MediaRating> ratings = mediaRatingRepository.getMediaRatingsByMediaIdLimitedTo(mediaId, pageable);

			logger.info("The media with the media id " + mediaId + " has " + ratings.size() + " reviews");

			return new ResponseEntity<>(ratings, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/averageRating/{id}")
	public ResponseEntity<Float> getAverageRatingByMediaId(@PathVariable("mediaId") long mediaId) {

		try {
			Float avgRating = mediaRatingRepository.getAverageRatingByMediaId(mediaId);
			
			if(avgRating == null) {
				logger.warn("The media with the media id " + mediaId + " does not exist!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(avgRating, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
