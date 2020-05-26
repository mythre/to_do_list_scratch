package com.todo.api;

import com.todo.impl.DatabaseConfig;
import com.todo.impl.ListDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.core.MediaType;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class ToDoAPI {

    @GET
    public Response getTodos() {
        try {
            ApplicationContext ctx = new AnnotationConfigApplicationContext(DatabaseConfig.class);
            ListDAO list_obj = ctx.getBean(ListDAO.class);
            ResultSet Result = list_obj.getTasksByUserId(1);
            Gson gson = new Gson();
            String result_json = gson.toJson(Result.next());
            return Response.ok().entity(result_json).build();
        } catch (SQLException sq) {
            sq.printStackTrace();
            return Response.ok().entity("error").build();
        }
    }
}
