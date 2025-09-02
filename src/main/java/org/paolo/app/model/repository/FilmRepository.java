package org.paolo.app.model.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.paolo.app.model.Film;
import org.paolo.app.model.Film$;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class FilmRepository {
    @Inject
    JPAStreamer jpaStreamer;

    private static final int PAGE_SIZE = 20;

    // Using JPAStreamer, I can write queries as if they were a Java Stream
    public Optional<Film> getFilm(Short filmId) {
        return jpaStreamer.stream(Film.class).filter(Film$.filmId.in(filmId)).findFirst();
    }

    public Stream<Film> getFilms(short minLength) {
        return jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minLength))
                .sorted(Film$.length);
    }

    // Paged query. I divide the results in pages of size PAGE_SIZE (with limit) and I only retrieve the page specified (with skip).
    // The query returns films with a length higher than minLength, in page "page"
    public Stream<Film> paged(long page, short minLength) {
        return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))   // Projection in order to select only the columns I'm interested in. You must always specify the primary key in the projection
                .filter(Film$.length.greaterThan(minLength))
                .sorted(Film$.length)
                .skip(page * PAGE_SIZE)
                .limit(PAGE_SIZE);
    }

    public Stream<Film> actors(String startsWith, short minLength) {
        final StreamConfiguration<Film> sc =
                StreamConfiguration.of(Film.class).joining(Film$.actors);
        return jpaStreamer.stream(sc)
                .filter(Film$.title.startsWith(startsWith).and(Film$.length.greaterThan(minLength)))
                .sorted(Film$.length.reversed());
    }

    // Change the price of movies longer than a certain length. A bit unconventional, but it is used for demonstration purposes only
    @Transactional  // When updating the DB, you have to use the keyword transactional to make the update effective
    public void updateRentalRate(short minLength, BigDecimal rentalRate) {
        jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minLength))
                .forEach(f -> {
                    f.setRentalRate(rentalRate);
                });
    }
}
