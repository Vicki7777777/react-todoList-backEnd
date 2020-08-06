package com.thoughtworks.react_todoList.service;

import com.thoughtworks.react_todoList.dto.TodoRequest;
import com.thoughtworks.react_todoList.dto.TodoResponse;
import com.thoughtworks.react_todoList.mapper.TodoMapper;
import com.thoughtworks.react_todoList.model.Todo;
import com.thoughtworks.react_todoList.respority.TodoRespority;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class todoListServiceTest {

    private final TodoMapper todoMapper =new TodoMapper();
    private final TodoRespority todoRespority = mock(TodoRespority.class);
    private final TodoListService todoListService = new TodoListService(todoMapper,todoRespority);

    @Test
    void should_return_given_todo_when_post_given_todo() throws Exception {
        //given
        TodoRequest todoRequest = new TodoRequest("test",false);
        when(todoRespority.post(todoMapper.toTodo(todoRequest))).thenReturn(todoMapper.toTodo(todoRequest));
        //when
        TodoResponse TodoResponse = todoListService.addTodo(todoRequest);
        //then
        assertNotNull(TodoResponse);
        assertEquals(todoRequest.getContent(),TodoResponse.getContent());
    }

    @Test
    void should_return_wrong_message_when_post_null_todo(){
        //given
        TodoRequest todoRequest = null;
        when(todoRespority.post(null)).thenReturn(null);
        //when
        Throwable exception = assertThrows(Exception.class,
                () -> todoListService.addTodo(todoRequest));
        //then
        assertEquals(new Exception("unsuccessfully!").getMessage(),exception.getMessage());
    }

    @Test
    void should_return_all_todos_when_get_given_nothing(){
        //given
        Todo todo1 = new Todo("test1",false);
        Todo todo2 = new Todo("test2",false);
        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo1);
        todoList.add(todo2);
        when(todoRespority.get()).thenReturn(todoList);
        //when
        List<TodoResponse> todoResponses = todoListService.getAllTodo();
        //then
        assertEquals(todoList.size(),todoResponses.size());

    }
}
