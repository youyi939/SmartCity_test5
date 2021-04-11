package com.example.smartcity_test5.bus.pojo;

public class Station {
    private String stepsId;
    private String name;
    private String sequence;

    public Station(String stepsId, String name, String sequence) {
        this.stepsId = stepsId;
        this.name = name;
        this.sequence = sequence;
    }

    public String getStepsId() {
        return stepsId;
    }

    public void setStepsId(String stepsId) {
        this.stepsId = stepsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
