package com.todo.impl;


import com.todo.impl.TodoDAO;
import com.todo.model.Todo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoDAOImpl implements TodoDAO {
    ApplicationContext ctx;
    ListDAO list_obj;
    JedisConfig jedisConfig;
    public TodoDAOImpl() {
        this.ctx = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        this.list_obj = ctx.getBean(ListDAO.class);
        this.jedisConfig = ctx.getBean(JedisConfig.class);
    }
    @Override
    public List<Todo> listAllTodos(int userId)  {
        List<Todo> todos = new ArrayList<>();
        ResultSet Result = null ;

        final JedisPoolConfig poolConfig = jedisConfig.buildPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "localhost");
        try (Jedis jedis = jedisPool.getResource()) {
            System.out.println(jedis.get("employee"));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    
    @Override
    public void addNewTodo(Todo todo) {
        try {
            list_obj.insertTask(todo);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
