package com.todo.impl;


import com.todo.impl.TodoDAO;
import com.todo.model.Todo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoDAOImpl implements TodoDAO {
    @Override
    public List<Todo> listAllTodos(int userId)  {
        List<Todo> todos = new ArrayList<>();
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        ListDAO list_obj = ctx.getBean(ListDAO.class);
        ResultSet Result = null ;
        try {
            Result = list_obj.getTasksByUserId(userId);

            while (Result.next())
            {
                Todo todo =  new Todo();
                todo.setTaskId(Result.getInt("taskId"));
                todo.setTaskItem(Result.getString("taskItem"));
                todo.setUserId(userId);
                todos.add(todo);
            }
        }
        catch(SQLException sq){
            sq.printStackTrace();
            return todos;
        }

        return todos;
    }
}
