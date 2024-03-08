package com.movierator.movierator.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Entity
@Table(name = MediaRating.TABLE, indexes = @Index(name = "mediaId", columnList = "mediaId"))
public class MediaRating implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String TABLE = "media_rating";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@DecimalMin(value = "0", message = "mediaId is required")
	private long mediaId;

	@DecimalMax(value = "5", message = "Rating must be less or equal 6")
	@DecimalMin(value = "0", message = "Rating must be more or equal 0")
	private int rating;

	@Column(columnDefinition = "TEXT")
	private String reviewText;
	
	@Column(nullable = false, updatable = false, name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(nullable = false, updatable = true, name = "last_modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;

	@ManyToOne(optional = false, targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getMediaId() {
		return mediaId;
	}

	public void setMediaId(long mediaId) {
		this.mediaId = mediaId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
}
