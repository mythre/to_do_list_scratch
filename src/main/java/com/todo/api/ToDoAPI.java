package com.todo.api;


import com.todo.model.Todo;
import com.todo.impl.TodoDAO;
import com.todo.impl.TodoDAOImpl;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.core.MediaType;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class ToDoAPI {
    TodoDAO todoDAO;
    public ToDoAPI()
    {
        todoDAO = new TodoDAOImpl();
    }

    @GET
    @Path("/{userId}")
    public Response getTodos(@PathParam("userId") Integer userId) {

            List<Todo> todos = todoDAO.listAllTodos(userId);
            Gson gson = new Gson();
            String result_json = gson.toJson(todos);
            return Response.ok().entity(result_json).build();

    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response addNewTodo(Todo todo) {
        todoDAO.addNewTodo(todo);
        return Response.ok().build();
    }
 }

