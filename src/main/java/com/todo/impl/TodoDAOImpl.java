package com.todo.impl;


import com.google.gson.Gson;
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

        Gson gson = new Gson();
        Todo cachedTodo;
        String todoJson;

        final JedisPoolConfig poolConfig = jedisConfig.buildPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, "localhost");
        try (Jedis jedis = jedisPool.getResource()) {
            List<String> todosString = jedis.lrange("userid_"+userId, 0, -1);
            for(String tS : todosString) {
                cachedTodo = gson.fromJson(tS, Todo.class);
                todos.add(cachedTodo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!todos.isEmpty()) {
            System.out.println("Retrieved from Cache!!");
            return todos;
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
                todoJson = gson.toJson(todo);
                try (Jedis jedis = jedisPool.getResource()) {
                    jedis.lpush("userid_"+userId, todoJson);
                }
            }
        } catch(Exception sq){
            sq.printStackTrace();
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
