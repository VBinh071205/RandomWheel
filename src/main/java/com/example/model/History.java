package com.example.model;

public class History {
    private int id;
    private long spinTime;
    private int wheelId;
    private String rewardName;

    public History() {
    }

    public History(int id, long spinTime, int wheelId, String rewardName) {
        this.id = id;
        this.spinTime = spinTime;
        this.wheelId = wheelId;
        this.rewardName = rewardName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSpinTime() {
        return spinTime;
    }

    public void setSpinTime(long spinTime) {
        this.spinTime = spinTime;
    }

    public int getWheelId() {
        return wheelId;
    }

    public void setWheelId(int wheelId) {
        this.wheelId = wheelId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }
}