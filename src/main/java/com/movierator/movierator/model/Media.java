package com.movierator.movierator.model;

public class Media {
	private long id;
	private MediaType type;
	private String name;

	public Media(long id, MediaType type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public MediaType getType() {
		return this.type;
	}
}
