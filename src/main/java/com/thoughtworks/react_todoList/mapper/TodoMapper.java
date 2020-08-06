package com.thoughtworks.react_todoList.mapper;

import com.thoughtworks.react_todoList.dto.TodoRequest;
import com.thoughtworks.react_todoList.dto.TodoResponse;
import com.thoughtworks.react_todoList.model.Todo;

public class TodoMapper {
    public Todo toTodo(TodoRequest todoRequest){
        return new Todo(todoRequest.getId(),todoRequest.getContent(),todoRequest.getStatus());
    }

    public TodoResponse todoResponse(Todo todo){
        return new TodoResponse(todo.getId(),todo.getContent(),todo.getStatus());
    }
}
