package ru.alxstn.carsharing.menu.basic;

import java.util.Scanner;

public interface ConsoleReader {
    static Scanner scan = new Scanner(System.in);

    default String readInputString() {
        return scan.nextLine();
    }
}
