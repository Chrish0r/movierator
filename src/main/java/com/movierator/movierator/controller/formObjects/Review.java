package com.movierator.movierator.controller.formObjects;

public class Review {
	
	private String reviewText;
	private int rating;
	
	public Review() {
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewMessage) {
		this.reviewText = reviewMessage;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int ratingScore) {
		this.rating = ratingScore;
	}

}
