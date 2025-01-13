package com.example.model;

public class Reward {
    private int id;
    private String name;
    private int wheelId;

    public Reward() {
    }
    public Reward(String name) {
        this.name = name;
    }

    public Reward(int id, String name, int wheelId) {
        this.id = id;
        this.name = name;
        this.wheelId = wheelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWheelId() {
        return wheelId;
    }

    public void setWheelId(int wheelId) {
        this.wheelId = wheelId;
    }
}