package com.todo;

import com.todo.api.ToDoAPI;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class ToDoApp extends ResourceConfig {

    public ToDoApp() {
        register(MultiPartFeature.class);
        register(ToDoAPI.class);
    }
}
