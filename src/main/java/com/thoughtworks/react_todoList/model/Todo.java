package com.thoughtworks.react_todoList.model;

public class Todo {
    private int id;
    private String content;
    private boolean status;

    public Todo(String content, boolean status) {
        this.content = content;
        this.status = status;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
