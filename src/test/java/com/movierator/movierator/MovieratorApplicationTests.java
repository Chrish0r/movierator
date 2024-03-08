package com.movierator.movierator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movierator.movierator.model.MediaRating;
import com.movierator.movierator.model.User;
import com.movierator.movierator.model.dto.RandomActivity;
import com.movierator.movierator.test.ActivityRestAPITester;
import com.movierator.movierator.test.MediaRatingRestAPITester;
import com.movierator.movierator.test.UserRestAPITester;

@SpringBootTest
class MovieratorApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UserRestAPITester clientUser;

	@Autowired
	public MediaRatingRestAPITester clientMediaRating;

	@Autowired
	public ActivityRestAPITester clientActivity;

	@Test
	public void userClientSuccessfullyReturnsUser() throws JsonProcessingException {

		User userResponse = this.clientUser.testGetUserAPI(1L);
		logger.info("Obtained following user: " + userResponse.getLogin());

		assertNotNull(userResponse);
		// in this case the following test scenario in not required (from a logical
		// point of view), but shown for demonstration purposes
		assertEquals(userResponse.getLogin(), "anna");
	}

	@Test
	public void userClientSuccessfullyReturnsAllActiveUsers() throws JsonProcessingException {

		List<User> userResponse = this.clientUser.testGetAllActiveUsersRestAPI();

		logger.info("Size of user list regading active users only: " + userResponse.size());

		assertNotNull(userResponse);
	}

	@Test
	public void activityClientSuccessfullyReturnsRandomActivity() throws JsonProcessingException {
		RandomActivity activityResponse = this.clientActivity.testGetActivityAPI();
		logger.info("Obtained following suggested random activity: " + activityResponse.toString());

		assertNotNull(activityResponse);
	}

	@Test
	public void mediaRatingClientSuccessfullyReturnsMediaRatings() throws JsonProcessingException {

		List<MediaRating> mediaRatingResponse = this.clientMediaRating.testGetMediaRatingsByMediaIdRestAPI(1L);

		logger.info("Size of media rating list: " + mediaRatingResponse.size());

		assertNotNull(mediaRatingResponse);
	}

	@Test
	public void mediaRatingClientSuccessfullyReturnsMediaRatingsByUser() throws JsonProcessingException {

		List<MediaRating> mediaRatingResponse = this.clientMediaRating.testGetMediaRatingsByUserIdRestAPI(1L);
		logger.info("Size of media rating list: " + mediaRatingResponse.size());

		assertNotNull(mediaRatingResponse);
	}

	@Test
	public void mediaRatingClientSuccessfullyReturnsMediaRatingsForMediaId() throws JsonProcessingException {

		Float avgRating = this.clientMediaRating.testGetAverageRatingsByMediaIdRestAPI(1L);

		logger.info("Average rating: " + avgRating);

		assertNotNull(avgRating);
	}

}
