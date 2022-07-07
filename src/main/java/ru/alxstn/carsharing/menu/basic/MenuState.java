package ru.alxstn.carsharing.menu.basic;

import java.util.*;

abstract class MenuState extends ReturnToPreviousState implements ConsoleReader {

    // Using LinkedHashMap for saving Entries by add order
    protected Map<Integer, MenuItem> menuEntries = new LinkedHashMap<>();
    private boolean showMenuNumbers = true;
    private String menuTitle = "";

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public MenuState(State returnToState) {
        super(returnToState);
    }

    public void setShowMenuNumbers(boolean showMenuNumbers) {
        this.showMenuNumbers = showMenuNumbers;
    }

    private void showMenu() {
        if (!(menuTitle.isEmpty() || menuTitle.isBlank()))
            System.out.println(menuTitle);

        for (var entry : menuEntries.entrySet()) {
            System.out.println((showMenuNumbers ? (entry.getKey() + ". ") : "" ) + entry.getValue().getDescription());
        }
    }

   MenuEntry<Integer, MenuItem> readMenuEntry() {
        showMenu();
        String str = readInputString();
        if (str.isEmpty() || str.isBlank()) {
            System.out.println("No input.");
            readMenuEntry();
        }
        try {
            int tryInt = Integer.parseInt(str);
            return readMenuEntryByNumber(tryInt);
        } catch (NumberFormatException nfe) {
            System.out.println("Error reading menu number: " + nfe.getMessage());
            return readMenuEntryByCommand(str);
        }
    }

    private MenuEntry<Integer, MenuItem> readMenuEntryByNumber(int selection) {
        if (!menuEntries.containsKey(selection)) {
            System.out.println("Error: unknown command!");
            readMenuEntry();
        }
        return new MenuEntry(selection, menuEntries.get(selection));
    }

    private MenuEntry<Integer, MenuItem> readMenuEntryByCommand(String selection) {
        if (!menuEntries.containsValue(new MenuItem(selection))) {
            System.out.println("Error: unknown command!");
            readMenuEntry();
        } else {
            for (var entry : menuEntries.entrySet()) {
                if (entry.getValue().getDescription().equals(selection)) {
                    return new MenuEntry(entry.getKey(), menuEntries.get(entry.getKey()));
                } // else System.out.println(entry.getValue().getDescription() + " != " + selection);
            }
        }
        return null;
    }

    @Override
    public State runState() {
        var option = readMenuEntry();
        return nextState((MenuEntry) option);
    }

    abstract State nextState(MenuEntry menuEntry);
}
