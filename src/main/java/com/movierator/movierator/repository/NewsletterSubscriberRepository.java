package com.movierator.movierator.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.movierator.movierator.model.NewsletterSubscriber;

public interface NewsletterSubscriberRepository extends CrudRepository<NewsletterSubscriber, Long> {
  List<NewsletterSubscriber> findByEmail(String email);
}
