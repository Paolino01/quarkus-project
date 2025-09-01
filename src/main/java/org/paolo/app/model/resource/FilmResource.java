package org.paolo.app.model.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.paolo.app.model.Film;
import org.paolo.app.model.repository.FilmRepository;

import java.util.Optional;

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
}
