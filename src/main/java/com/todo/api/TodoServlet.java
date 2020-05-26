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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/v1/todos")
public class TodoServlet extends HttpServlet {
    TodoDAO todoDAO;
    public void init() {
        todoDAO = new TodoDAOImpl();
    }

    @Override
    @Produces("application/json")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Todo> todos = todoDAO.listAllTodos();
        String todoJsonString = new Gson().toJson(todos);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(todoJsonString);
        out.flush();
    }
}
