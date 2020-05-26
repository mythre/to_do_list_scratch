package com.todo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Todo {
    private int taskId;
    private String taskItem;
    private OffsetDateTime completedDate;
    private OffsetDateTime modifiedDate;
    private boolean completed;
    private int userId;
}
