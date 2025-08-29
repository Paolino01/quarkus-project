package com.social.post.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    PostRepository repository;

    @GET
    public List<PostEntity> getAll() {
        return repository.listAll();
    }

    @POST
    @Transactional
    public PostEntity addPost(PostEntity post) {
        repository.persist(post);
        return post;
    }

    @GET
    @Path("/user/{userId}")
    public List<PostEntity> getPostsByUser(@PathParam("userId") Long userId) {
        return repository.find("userId", userId).list();
    }
}