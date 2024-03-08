package com.movierator.movierator.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Entity
@Table(name = Moderator.TABLE)
public class Moderator extends AbstractRole {
	
	private static final long serialVersionUID = -7384982391621229700L;
	public static final String TABLE = "moderators";
	
	@Column(name = "first_name")
	@Size(min =4, message = "First name must contain at least 2 characters!")
	private String firstName;
	@Column(name = "last_name")
	@Size(min =4, message = "Last name must contain at least 2 characters!")
	private String lastName;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@Valid 
	User user;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
