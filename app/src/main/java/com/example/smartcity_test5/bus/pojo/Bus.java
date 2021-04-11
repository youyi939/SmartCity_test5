package com.example.smartcity_test5.bus.pojo;

import java.util.List;

public class Bus {
    private String endTime;
    private int id;
    private String name;
    private String first;
    private String end;
    private String startTime;
    private double price;
    private String mileage;
    private List<Station> stationList;

    public Bus(String endTime, int id, String name, String first, String end, String startTime, double price, String mileage, List<Station> stationList) {
        this.endTime = endTime;
        this.id = id;
        this.name = name;
        this.first = first;
        this.end = end;
        this.startTime = startTime;
        this.price = price;
        this.mileage = mileage;
        this.stationList = stationList;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }
}
