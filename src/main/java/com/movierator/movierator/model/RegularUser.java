package com.movierator.movierator.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name = RegularUser.TABLE)
public class RegularUser extends AbstractRole {

	private static final long serialVersionUID = 5088115185535822969L;
	public static final String TABLE = "regular_users";
	
	public RegularUser() {
		
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@Valid 
	User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
