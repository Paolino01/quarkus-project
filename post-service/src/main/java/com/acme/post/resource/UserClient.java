package com.social.post.resource;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "user-service")
public interface UserClient {
    @GET
    @Path("/{id}")
    UserDTO getUser(@PathParam("id") Long id);
}