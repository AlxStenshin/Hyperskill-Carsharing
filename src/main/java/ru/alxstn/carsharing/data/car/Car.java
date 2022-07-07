package ru.alxstn.carsharing.data.car;

public class Car {
    private String name;
    private int id;

    public Car(String name) {
        this.name = name;
    }

    public Car(int id, String name) {
        this.name = name;
        this.id = id;
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
}
