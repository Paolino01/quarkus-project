package com.acme.user.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository repository;

    @GET
    public List<UserEntity> getAll() {
        return repository.listAll();
    }

    @POST
    @Transactional
    public UserEntity addUser(UserEntity user) {
        repository.persist(user);
        return user;
    }
}
