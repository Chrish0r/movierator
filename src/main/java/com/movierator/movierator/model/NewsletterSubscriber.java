package com.movierator.movierator.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = NewsletterSubscriber.TABLE, indexes = @Index(name = "email", columnList = "email", unique = true))
public class NewsletterSubscriber implements Serializable {
  private static final long serialVersionUID = 1L;
  static final String TABLE = "newsletter_subscribers";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Email(message = "Email is invalid")
  @NotEmpty(message = "Email is required")
  private String email;

  private Date subscribedAt;

  public NewsletterSubscriber() {
    subscribedAt = new Date();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getSubscribedAt() {
    return subscribedAt;
  }
}
