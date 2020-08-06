package com.thoughtworks.react_todoList.service;

import com.thoughtworks.react_todoList.dto.TodoRequest;
import com.thoughtworks.react_todoList.dto.TodoResponse;
import com.thoughtworks.react_todoList.mapper.TodoMapper;
import com.thoughtworks.react_todoList.model.Todo;
import com.thoughtworks.react_todoList.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoListService {
    private TodoMapper todoMapper;
    private TodoRepository todoRepository;
    public TodoListService(TodoMapper todoMapper, TodoRepository todoRepository) {
        this.todoMapper = todoMapper;
        this.todoRepository = todoRepository;
    }

    public TodoResponse addTodo(TodoRequest todoRequest) throws Exception{
        if(todoRequest == null){
            throw new Exception("unsuccessfully!");
        }
        Todo todo = todoRepository.save(todoMapper.toTodo(todoRequest));
        return todoMapper.todoResponse(todo);
    }

    public List<TodoResponse> getAllTodo() {
        List<Todo> todoList = todoRepository.findAll();
        return todoList.stream().map(todoMapper::todoResponse).collect(Collectors.toList());
    }

    public Boolean removeTodo(Integer id) {
        return null;
    }
}
