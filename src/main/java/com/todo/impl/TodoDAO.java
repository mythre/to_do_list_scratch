package com.todo.impl;

import com.todo.model.Todo;

import java.sql.SQLException;
import java.util.List;

public interface TodoDAO {
    List<Todo> listAllTodos(int userId) ;
}
