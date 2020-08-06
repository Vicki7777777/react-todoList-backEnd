package com.thoughtworks.react_todoList.dto;

public class TodoResponse {
    private Integer id;
    private String content;
    private boolean status;

    public TodoResponse(Integer id, String content, boolean status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

    public TodoResponse(String content, boolean status) {
        this.content = content;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
