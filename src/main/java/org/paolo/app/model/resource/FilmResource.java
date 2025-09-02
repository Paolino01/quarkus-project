package org.paolo.app.model.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.paolo.app.model.Film;
import org.paolo.app.model.repository.FilmRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.quarkus.arc.ComponentsProvider.LOG;

@Path("/")
public class FilmResource {
    @Inject
    FilmRepository filmRepository;

    @GET
    @Path("/hello-world")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello world";
    }

    @GET
    @Path("/film/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilm(@PathParam("filmId") Short filmId) {
        Optional<Film> film = filmRepository.getFilm(filmId);
        return film.isPresent() ? film.get().getTitle() : "No film was found!";
    }

    @GET
    @Path("/page-films/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String paged(@PathParam("page") long page, @PathParam("minLength") short minLength) {
        return filmRepository.paged(page, minLength)
                .map(f -> String.format("%s (%d min)", f.getTitle(), f.getLength()))    // Formats the string in the specified way
                .collect(Collectors.joining("\n"));     // Adds a line break between each element of the stream
    }

    @GET
    @Path("/page-films/{startsWith}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String paged(@PathParam("startsWith") String startsWith, @PathParam("minLength") short minLength) {
        return filmRepository.actors(startsWith, minLength)
                .map(f -> String.format("%s (%d min): %s",
                        f.getTitle(),
                        f.getLength(),
                        f.getActors().stream()
                                .map(a -> String.format("%s %s", a.getFirstName(), a.getLastName()))
                                .collect(Collectors.joining(", "))))
                .collect(Collectors.joining("\n"));
    }

    @PATCH
    @Path("/update-rental-rate/")
    @Produces(MediaType.TEXT_PLAIN)
    public String update(@QueryParam("minLength") short minLength, @QueryParam("rentalRate") BigDecimal rentalRate) {
        filmRepository.updateRentalRate(minLength, rentalRate);
        return filmRepository.getFilms(minLength)
                .map(f -> String.format("%s (%d min) - $%f", f.getTitle(), f.getLength(), f.getRentalRate()))
                .collect(Collectors.joining("\n"));
    }
}
