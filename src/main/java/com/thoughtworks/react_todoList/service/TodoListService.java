package com.thoughtworks.react_todoList.service;

import com.thoughtworks.react_todoList.mapper.TodoMapper;
import com.thoughtworks.react_todoList.model.Todo;
import com.thoughtworks.react_todoList.respority.TodoRespority;

public class TodoListService {
    private TodoMapper todoMapper;
    private TodoRespority todoRespority;
    public TodoListService(TodoMapper todoMapper, TodoRespority todoRespority) {
        this.todoMapper = todoMapper;
        this.todoRespority = todoRespority;
    }

    public Todo addTodo(Todo todo) throws Exception{
        if(todo == null){
            throw new Exception("unsuccessfully!");
        }
        Todo thisTodo = new Todo(todo.getContent(),todo.getStatus());
        return thisTodo;
    }
}
