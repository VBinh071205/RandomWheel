package com.example.model;

import java.util.Date;
import java.util.List;

public class Wheel {
    private int id;
    private String name;
    private Date createDate;
    private List<Reward> rewards;

    public Wheel() {
    }

    public Wheel(int id, String name, Date createDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
    }
    public Wheel( String name, Date createDate) {
        this.name = name;
        this.createDate = createDate;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return name;
    }
}