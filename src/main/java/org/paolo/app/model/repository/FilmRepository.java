package org.paolo.app.model.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.paolo.app.model.Film;
import org.paolo.app.model.Film$;

import java.util.Optional;

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class FilmRepository {
    @Inject
    JPAStreamer jpaStreamer;

    public Optional<Film> getFilm(Short filmId) {
        return jpaStreamer.stream(Film.class).filter(Film$.filmId.in(filmId)).findFirst();
    }
}
