package com.thoughtworks.react_todoList.service;

import com.thoughtworks.react_todoList.dto.TodoRequest;
import com.thoughtworks.react_todoList.dto.TodoResponse;
import com.thoughtworks.react_todoList.mapper.TodoMapper;
import com.thoughtworks.react_todoList.model.Todo;
import com.thoughtworks.react_todoList.respority.TodoRespority;

import java.util.List;

public class TodoListService {
    private TodoMapper todoMapper;
    private TodoRespority todoRespority;
    public TodoListService(TodoMapper todoMapper, TodoRespority todoRespority) {
        this.todoMapper = todoMapper;
        this.todoRespority = todoRespority;
    }

    public TodoResponse addTodo(TodoRequest todoRequest) throws Exception{
        if(todoRequest == null){
            throw new Exception("unsuccessfully!");
        }
        Todo todo = new Todo(todoRequest.getContent(),todoRequest.getStatus());
        return todoMapper.todoResponse(todo);
    }

    public List<TodoResponse> getAllTodo() {
        return null;
    }
}
