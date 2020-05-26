package com.todo.impl;

import com.todo.model.Todo;

import java.util.List;

public interface TodoDAO {
    List<Todo> listAllTodos();
}
