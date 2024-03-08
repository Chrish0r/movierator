package com.movierator.movierator.model;

public enum MediaType {
	MOVIE("MOVIE"),
	SERIES("SERIES");

	private String type;

	private MediaType(String type) {
		this.type = type;
	}

	public String toString() {
		return this.type;
	}
}
