package com.movierator.movierator.controller.formObjects;

import java.util.Date;

public class MediaRatingByUserSearchResult {
	
	private int rating;
	private String reviewText;
	private Date date;
	
	
	
	public MediaRatingByUserSearchResult(int rating, String reviewText, Date date) {
		this.rating = rating;
		this.reviewText = reviewText;
		this.date = date;
	}

	public int getRating() {
		return rating;
	}
	public String getReviewText() {
		return reviewText;
	}
	public Date getDate() {
		return date;
	}
	
	

}
