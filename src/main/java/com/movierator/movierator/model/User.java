package com.movierator.movierator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.movierator.movierator.service.AttributeEncryptor;

@Entity
@Table(name = User.TABLE)
public class User implements Serializable {

	private static final long serialVersionUID = -6853534067306206163L;

	public static final String TABLE = "users";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "User name is mandatory")
	@Column(name = "user_name", unique = true)
	private String login;

	@Email(message = "No valid email-address")
	@NotEmpty(message = "Email is mandatory")
	@Column(unique = true)
	private String email;

	@NotEmpty(message = "Password is mandatory")
	@Size(min = 4, message = "Password must contain at least 4 characters")
	private String password;

	private Integer active;

	@Column(columnDefinition = "varchar(255)")
	@Convert(converter = AttributeEncryptor.class)
	private String birthday;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_authorities", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private List<Authority> myAuthorities = new ArrayList<Authority>();

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Authority> getMyAuthorities() {
		return myAuthorities;
	}

	public void setMyAuthorities(List<Authority> myAuthorities) {
		this.myAuthorities = myAuthorities;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}

		User other = (User) obj;
		return this.getId() == other.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(TABLE, this.getId());
	}

}
