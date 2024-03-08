package com.movierator.movierator.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.movierator.movierator.model.NewsletterSubscriber;
import com.movierator.movierator.repository.NewsletterSubscriberRepository;

@Controller
public class NewsletterSubscriberController {
  private NewsletterSubscriberRepository newsletterSubscriberRepository;

  public NewsletterSubscriberController(NewsletterSubscriberRepository newsletterSubscriberRepository) {
    this.newsletterSubscriberRepository = newsletterSubscriberRepository;
  }

  @PostMapping("/newsletter-subscriber")
  public String subscribeToNewsletter(@Valid NewsletterSubscriber newsletterSubscriber, BindingResult result) {
    if(result.hasErrors()) {
      return "index";
    }

    if(!this.newsletterSubscriberRepository.findByEmail(newsletterSubscriber.getEmail()).isEmpty()) {
      return "newsletter/already-subscribed";
    }

    this.newsletterSubscriberRepository.save(newsletterSubscriber);

    return "newsletter/subscribe-success";
  }

  @GetMapping("/newsletter-subscriber/{id}")
  public String getNewsletterSubscriber(@PathVariable Long id, Model model) {
    Optional<NewsletterSubscriber> subscriber = newsletterSubscriberRepository.findById(id);
    
    if(subscriber.isEmpty()) {
      return "newsletter/subscriber-not-found";
    }

    model.addAttribute("newsletterSubscriber", subscriber);

    return "newsletter/subscriber";
  }

  @PutMapping("/newsletter-subscriber/{id}")
  public String updateNewsletterSubscriber(@PathVariable Long id, @Valid NewsletterSubscriber newsletterSubscriber, BindingResult result) {
    if(result.hasErrors()) {
      return "newsletter/subscriber";
    }

    Optional<NewsletterSubscriber> oldNewsletterSubscriberOpt = newsletterSubscriberRepository.findById(id);
    if(oldNewsletterSubscriberOpt.isEmpty()) {
      return "newsletter/subscriber-not-found";
    }

    NewsletterSubscriber oldNewsletterSubscriber = oldNewsletterSubscriberOpt.get();

    oldNewsletterSubscriber.setEmail(newsletterSubscriber.getEmail());
    this.newsletterSubscriberRepository.save(oldNewsletterSubscriber);

    return "newsletter/subscriber-updated";
  }

  @DeleteMapping("/newsletter-subscriber/{id}")
  public String deleteNewsletterSubscriber(@PathVariable Long id) {
    newsletterSubscriberRepository.deleteById(id);

    return "newsletter/unsubscribe-success";
  }
}
