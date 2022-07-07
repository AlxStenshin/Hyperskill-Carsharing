package ru.alxstn.carsharing.menu.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class ConfigurableMenuState extends MenuState {

    private Map<Integer, MenuItem> menus = super.menuEntries;
    private Map<Integer, UnaryOperator<State>> transitions = new HashMap<>();

    public ConfigurableMenuState(State returnToState) {
        super(returnToState);
        this.setMenuTitle("\n");
    }

    public void addMenuItem(int id, MenuItem item,  UnaryOperator<State> nextState) {
        menus.put(id, item);
        transitions.put(id, nextState);
    }

    @Override
    State nextState(MenuEntry menuEntry) {
        var op = transitions.get(menuEntry.getId());
        return op.apply(this);
    }
}
