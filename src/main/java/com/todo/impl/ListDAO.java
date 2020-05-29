package com.todo.impl;

import com.todo.model.Todo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

public class ListDAO {
    private  PostgresSql postgresSql ;
    private final Connection conn;

    public ListDAO(PostgresSql postgresSql)
    {
        this.postgresSql = postgresSql;
        conn = postgresSql.getConnection();
    }
    public static  final String Query_getTasks_By_UserId = "SELECT taskId,taskItem FROM tasks WHERE userId = ?";
    public static final String Query_update_completed_by_taskId = "UPDATE tasks SET completed = ? WHERE taskId = ?";
    public static final String Query_update_taskItem_by_taskId = "UPDATE tasks SET taskItem = ? WHERE taskId = ?";
    public static final String  Query_delete_by_taskId = "DELETE FROM tasks WHERE taskId IN (%s)";
    private static final String Query_insert_todo = "INSERT INTO tasks (TASKITEM, USERID) VALUES (?,?);";


    public ResultSet getTasksByUserId(int userId) throws SQLException
    {
        PreparedStatement stmt=conn.prepareStatement(Query_getTasks_By_UserId);
        stmt.setInt(1,userId);
        ResultSet tasks_by_userid = stmt.executeQuery();
        return tasks_by_userid;
    }
    public int updateTaskCompletionByTaskId(boolean completed,int taskId) throws SQLException
    {
        PreparedStatement stmt=conn.prepareStatement(Query_update_completed_by_taskId);
        stmt.setBoolean(1,completed);
        stmt.setInt(2,taskId);
        int completedUpdateSucces = stmt.executeUpdate();
        return completedUpdateSucces;
    }

    public int updateTaskItemByTaskId(String taskItem,int taskId) throws SQLException
    {
        PreparedStatement stmt=conn.prepareStatement(Query_update_taskItem_by_taskId);
        stmt.setString(1,taskItem);
        stmt.setInt(2,taskId);
        int taskItemUpdateSuccess = stmt.executeUpdate();
        return taskItemUpdateSuccess;
    }

    public int deleteTasksByTaskId(List<Integer> taskIds) throws SQLException {
        String finalQuery = String.format(Query_delete_by_taskId,getInPlaceHolders(taskIds.size()));
        PreparedStatement stmt=conn.prepareStatement(finalQuery);
        stmt =  populateListInQuery(stmt,taskIds);
        int tasksDeletionSuccess = stmt.executeUpdate();
        return tasksDeletionSuccess;
    }

    public void insertTask(Todo todo) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(Query_insert_todo);
        preparedStatement.setString(1, todo.getTaskItem());
        preparedStatement.setInt(2, todo.getUserId());
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

        public static PreparedStatement populateListInQuery(PreparedStatement statement, List<Integer> items) throws SQLException, SQLException {
        int index = 1;
        for (int item : items) {
            statement.setInt(index++, item);
        }
        return statement;
    }

    public static String getInPlaceHolders(int length) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; ) {
            builder.append('?');
            if (++index < length) {
                builder.append(',');
            }

        }
        return builder.toString();
    }
}
