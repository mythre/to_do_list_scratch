package com.todo.impl;
import org.springframework.context.annotation.*;
import java.sql.SQLException;
@Configuration
public class DatabaseConfig{
    @Bean
    public PostgresSql postgresSql(){
        return new PostgresSql();
    }
    @Bean
    public ListDAO listDAO() throws SQLException {
        return new ListDAO(postgresSql());
    }

}




