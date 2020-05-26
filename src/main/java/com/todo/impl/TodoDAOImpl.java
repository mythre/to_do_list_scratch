package com.todo.impl;

import com.todo.impl.TodoDAO;
import com.todo.model.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoDAOImpl implements TodoDAO {
    @Override
    public List<Todo> listAllTodos() {
        List<Todo> todos = new ArrayList<>();

        todos.add(new Todo(1, "Test todo", LocalDateTime.now(), null, false, 2 ));
        return todos;
    }
}
