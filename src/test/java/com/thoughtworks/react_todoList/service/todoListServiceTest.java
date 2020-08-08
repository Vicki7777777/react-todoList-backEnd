package com.thoughtworks.react_todoList.service;

import com.thoughtworks.react_todoList.dto.TodoRequest;
import com.thoughtworks.react_todoList.dto.TodoResponse;
import com.thoughtworks.react_todoList.mapper.TodoMapper;
import com.thoughtworks.react_todoList.model.Todo;
import com.thoughtworks.react_todoList.repository.TodoRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class todoListServiceTest {

    private final TodoMapper todoMapper = mock(TodoMapper.class);
    private final TodoRepository todoRepository = mock(TodoRepository.class);
    private final TodoListService todoListService = new TodoListService(todoMapper,todoRepository);

    @Test
    void should_return_given_todo_when_post_given_todo() throws Exception {
        //given
        TodoRequest todoRequest = new TodoRequest("test", false);
        Todo todo = new Todo("test", false);
        TodoResponse todoResponse = new TodoResponse("test", false);
        given(todoMapper.toTodo(todoRequest)).willReturn(todo);
        given(todoRepository.save(todo)).willReturn(todo);
        given(todoMapper.todoResponse(todo)).willReturn(todoResponse);
        //when
        TodoResponse TodoResponseTest = todoListService.addTodo(todoRequest);
        //then
        assertNotNull(TodoResponseTest);
        assertEquals(todoRequest.getContent(),TodoResponseTest.getContent());
    }

    @Test
    void should_return_wrong_message_when_post_null_todo() {
        //given
        when(todoRepository.save(null)).thenReturn(null);
        //when
        Throwable exception = assertThrows(Exception.class,
                () -> todoListService.addTodo(null));
        //then
        assertEquals(new Exception("unsuccessfully!").getMessage(), exception.getMessage());
    }

    @Test
    void should_return_all_todos_when_get_given_nothing() {
        //given
        Todo todo1 = new Todo("test1", false);
        Todo todo2 = new Todo("test2", false);
        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo1);
        todoList.add(todo2);
        when(todoRepository.findAll()).thenReturn(todoList);
        //when
        List<TodoResponse> todoResponses = todoListService.getAllTodo();
        //then
        assertEquals(todoList.size(), todoResponses.size());
    }

    @Test
    void should_return_true_when_remove_given_right_id() throws Exception {
        //given
        Integer id = 1;
        Todo todo = new Todo(1,"test", false);
        when(todoRepository.findById(id)).thenReturn(java.util.Optional.of(todo));
        //when
        Boolean isDelete = todoListService.removeTodo(id);
        //then
        assertTrue(isDelete);
    }

    @Test
    void should_return_wrong_message_when_remove_given_non_existent_id(){
        //given
        Integer id = 1;
        when(todoRepository.findById(id)).thenReturn(null);
        //when
        Throwable exception = assertThrows(Exception.class,
                () -> todoListService.removeTodo(id));
        //then
        assertEquals(new Exception("unsuccessfully!").getMessage(), exception.getMessage());
    }

    @Test
    void should_return_todo_message_when_update_given_right_todo() throws Exception {
        //given
        Integer id = 1;
        TodoRequest todoRequest = new TodoRequest( "test", false);
        TodoRequest updateTodoRequest = new TodoRequest( "test", true);
        Todo todo = new Todo("test", false);
        Todo updateTodo = new Todo("test", true);
        TodoResponse todoResponse = new TodoResponse("test", true);
        TodoResponse updateTodoResponse = new TodoResponse("test", true);
        given(todoRepository.findById(id)).willReturn(Optional.of(todo));
        given(todoRepository.save(todo)).willReturn(todo);
        given(todoMapper.toTodo(todoRequest)).willReturn(todo);
        given(todoMapper.toTodo(updateTodoRequest)).willReturn(updateTodo);
        given(todoMapper.todoResponse(todo)).willReturn(todoResponse);
        given(todoMapper.todoResponse(updateTodo)).willReturn(updateTodoResponse);
        given(todoRepository.save(updateTodo)).willReturn(updateTodo);
        //when
        TodoResponse todoResponseTest = todoListService.updateTodo(id,updateTodoRequest);
        //then
        assertEquals(updateTodo.getStatus(),todoResponseTest.getStatus());
    }

    @Test
    void should_return_wrong_message_when_update_given_non_existent_id(){
        //given
        Integer id = 1;
        TodoRequest todoRequest = new TodoRequest( "test", false);
        given(todoRepository.findById(id)).willReturn(null);
        //when
        Throwable exception = assertThrows(Exception.class,
                () -> todoListService.updateTodo(id,todoRequest));
        //then
        assertEquals(new Exception("unsuccessfully!").getMessage(), exception.getMessage());
    }
}
