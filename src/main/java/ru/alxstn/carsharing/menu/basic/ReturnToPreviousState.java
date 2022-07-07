package ru.alxstn.carsharing.menu.basic;

public abstract class ReturnToPreviousState implements State {
    protected State returnToState;

    public ReturnToPreviousState(State returnToState) {
        this.returnToState = returnToState;
    }
}
