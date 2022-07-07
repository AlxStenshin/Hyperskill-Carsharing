package ru.alxstn.carsharing.menu.basic;

public class MenuEntry<T, U> {
    private final T id;
    private final U value;

    public MenuEntry(T id, U value) {
        this.id = id;
        this.value = value;
    }

    public T getId() {
        return id;
    }

    public U getValue() {
        return value;
    }

}

