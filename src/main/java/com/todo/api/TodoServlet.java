package com.todo.api;

import com.google.gson.Gson;
import com.todo.impl.TodoDAO;
import com.todo.impl.TodoDAOImpl;
import com.todo.model.Todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/v1/todos/*")
public class TodoServlet extends HttpServlet {
    TodoDAO todoDAO;
    public void init() {
        todoDAO = new TodoDAOImpl();
    }

    @Override
    @Produces("application/json")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo=req.getPathInfo();
        Integer userId;
        try{
            String[] pathParts = pathInfo.split("/");
            System.out.println(Arrays.toString(pathParts));
            userId = (pathParts.length>0)?Integer.valueOf(pathParts[1]):0;
            System.out.println(userId);}
        catch(Exception exception)
        {
            exception.printStackTrace();
            userId=0;
        }
        List<Todo> todos = todoDAO.listAllTodos(userId);
        String todoJsonString = new Gson().toJson(todos);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(todoJsonString);
        out.flush();
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
    {
        BufferedReader reader = req.getReader();
        Todo todo = new Gson().fromJson(reader,Todo.class);
        todoDAO.addNewTodo(todo);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print("");
        out.flush();
    }
}
