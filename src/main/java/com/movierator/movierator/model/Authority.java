package com.movierator.movierator.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Authority.TABLE)
public class Authority implements Serializable {

	private static final long serialVersionUID = -7279536509954804901L;

	public static final String TABLE = "authorities";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	


	public Authority(Long authority_id) {
		this.id = authority_id;
	}

	public Authority() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
