package com.movierator.movierator.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.movierator.movierator.model.Actor;
import com.movierator.movierator.tmdbApi.TMDBActor;
import com.movierator.movierator.tmdbApi.TMDBActorResponse;
import com.movierator.movierator.tmdbApi.TMDBApi;
import com.movierator.movierator.tmdbApi.TMDBApiFactory;

@Component
public class ActorRepository implements Repository<Actor, Long> {
  private TMDBApi<TMDBActorResponse, TMDBActor> actorApi;

  public ActorRepository(TMDBApiFactory tmdbApiFactory) {
    actorApi = tmdbApiFactory.createForActors();
  }

  public List<Actor> findByName(String name) {
    // Use TMDB API to search directly from API
    TMDBActorResponse actorResponse = actorApi.search(name);

    return TMDBActorMapper.tmdbActorsToActors(actorResponse.results);
  }

  public Optional<Actor> findById(long id) {
    Optional<TMDBActor> tmdbActor = actorApi.findById(id);

    if(tmdbActor.isPresent()) {
      return Optional.of(TMDBActorMapper.tmdbActorToActor(tmdbActor.get()));
    } else {
      return Optional.empty();
    }
  }
}

class TMDBActorMapper {
  static List<Actor> tmdbActorsToActors(TMDBActor[] tmdbActors) {
    List<Actor> actors = new ArrayList<>();

    for (TMDBActor tmdbActor : tmdbActors) {
      actors.add(TMDBActorMapper.tmdbActorToActor(tmdbActor));
    }

    return actors;
  }

  static Actor tmdbActorToActor(TMDBActor tmdbActor) {
    return new Actor(tmdbActor.id, tmdbActor.name);
  }
}