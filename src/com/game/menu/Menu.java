package com.game.menu;

public abstract class Menu {
    public abstract void menu();
    public abstract void menuText();
    public void clearConsole() {
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }
}
