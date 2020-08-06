package com.thoughtworks.react_todoList.service;

import com.thoughtworks.react_todoList.mapper.TodoMapper;
import com.thoughtworks.react_todoList.model.Todo;
import com.thoughtworks.react_todoList.respority.TodoRespority;
import org.junit.jupiter.api.Test;

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
        Todo todo = new Todo("test",false);
        when(todoRespority.post(todo)).thenReturn(todo);
        //when
        Todo actualTodo = todoListService.addTodo(todo);
        //then
        assertNotNull(actualTodo);
        assertEquals(todo.getContent(),actualTodo.getContent());
        assertEquals(todo.getStatus(),actualTodo.getStatus());
    }

    @Test
    void should_return_wrong_message_when_post_null_todo(){
        //given
        Todo todo = null;
        when(todoRespority.post(null)).thenReturn(null);
        //when
        Throwable exception = assertThrows(Exception.class,
                () -> todoListService.addTodo(todo));
        //then
        assertEquals(new Exception("unsuccessfully!").getMessage(),exception.getMessage());
    }
}
