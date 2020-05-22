package com.todo.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/todos")
public class ToDoAPI {

    @GET
    public Response getTodos()
    {
        return Response.ok().entity("Test").build();
    }
}
