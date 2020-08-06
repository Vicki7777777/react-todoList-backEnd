package com.thoughtworks.react_todoList.controller;

import com.thoughtworks.react_todoList.dto.TodoRequest;
import com.thoughtworks.react_todoList.dto.TodoResponse;
import com.thoughtworks.react_todoList.model.Todo;
import com.thoughtworks.react_todoList.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/todos")
public class TodoListController {
    @Autowired
    private TodoListService todoListService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getTodoList(){
        return todoListService.getAllTodo();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TodoResponse updateTodo(@PathVariable Integer id, @RequestBody TodoRequest todoRequest) throws Exception {
        return todoListService.updateTodo(id,todoRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResponse createTodo(@RequestBody TodoRequest todoRequest) throws Exception {
        return todoListService.addTodo(todoRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public boolean removeTodo(@PathVariable int id) throws Exception {
        return todoListService.removeTodo(id);
    }


}

