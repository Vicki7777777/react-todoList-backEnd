package com.thoughtworks.react_todoList.service;

import com.thoughtworks.react_todoList.mapper.TodoMapper;
import com.thoughtworks.react_todoList.model.Todo;
import com.thoughtworks.react_todoList.respority.TodoRespority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class todoListServiceTest {

    private final TodoMapper todoMapper =new TodoMapper();
    private final TodoRespority todoRespority = mock(TodoRespority.class);
    private final TodoListService todoListService = new TodoListService(todoMapper,todoRespority);

    @Test
    void should_return_given_todo_when_post_given_todo(){
        //given
        Todo todo = new Todo("test",false);
        //when
        Todo actualTodo = todoListService.addTodo(todo);
        //then
        assertNotNull(actualTodo);
//        assertEquals(todo.getContent(),actualTodo.getContent());
//        assertEquals(todo.getStatus(),actualTodo.getStatus());
    }
}
