package com.movierator.movierator.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.movierator.movierator.model.NewsletterSubscriber;
import com.movierator.movierator.repository.NewsletterSubscriberRepository;
import com.movierator.movierator.tmdbApi.TMDBApiFactory;
import com.movierator.movierator.tmdbApi.TMDBMovie;
import com.movierator.movierator.tmdbApi.TMDBMovieApi;

@Service
public class NewsletterSender {
  private final Logger logger = LoggerFactory.getLogger(NewsletterSender.class);

  @Autowired
  private NewsletterSubscriberRepository newsletterSubscriberRepository;
  @Autowired
  private MailSender emailSender;
  private TMDBMovieApi tmdbMovieApi;

  public NewsletterSender(TMDBApiFactory tmdbApiFactory) {
    tmdbMovieApi = tmdbApiFactory.createForMovies();
  }

  // @Scheduled(fixedRate = 60000) // For testing purposes
  @Scheduled(cron = "0 0 19 * * MON") // Every monday at 7 PM
  public void sendNewsletter() {
    logger.info("Sending newsletter...");
    Iterable<NewsletterSubscriber> subscribers = newsletterSubscriberRepository.findAll();

    List<String> recipients = new ArrayList<>();
    for (NewsletterSubscriber newsletterSubscriber : subscribers) {
      recipients.add(newsletterSubscriber.getEmail());
    }
    if (recipients.isEmpty()) {
      logger.info("No newsletter subscribers. Skip sending.");
      return;
    }

    SimpleMailMessage message = getNewsletterMessage();
    message.setTo((recipients.toArray(new String[0])));
    emailSender.send(message);

    logger.info(String.format("Sent newsletter to %d subscribers", recipients.size()));
  }

  private SimpleMailMessage getNewsletterMessage() {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("info@movie-rator.de");
    message.setSubject("Newsletter");
    message.setText(getNewsletterContent());

    return message;
  }

  private String getNewsletterContent() {
    List<TMDBMovie> upcomingMovies = tmdbMovieApi.getUpcoming();

    String content = "Upcoming movies: \n";
    for (TMDBMovie tmdbMovie : upcomingMovies) {
      content += String.format("%s on %s\n", tmdbMovie.title, tmdbMovie.release_date);
    }

    return content;
  }
}
