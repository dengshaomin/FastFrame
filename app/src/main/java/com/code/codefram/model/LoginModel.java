package com.code.codefram.model;

import java.util.List;

public class LoginModel {

    String name;

    int id;

    List<String> learns;

    public LoginModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getLearns() {
        return learns;
    }

    public void setLearns(List<String> learns) {
        this.learns = learns;
    }

    public LoginModel(String name, int id, List<String> learns) {
        this.name = name;
        this.id = id;
        this.learns = learns;
    }
}
