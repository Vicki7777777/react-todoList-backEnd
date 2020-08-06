package com.thoughtworks.react_todoList.repository;

import com.thoughtworks.react_todoList.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {

}
