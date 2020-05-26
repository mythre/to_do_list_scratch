package com.todo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private int taskId;
    private String taskItem;
    private LocalDateTime completedDate;
    private LocalDateTime modifiedDate;
    private boolean completed;
    private int userId;
}
