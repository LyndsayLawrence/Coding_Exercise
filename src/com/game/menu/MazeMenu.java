package com.game.menu;

import com.game.exceptions.UnknownErrorException;
import com.game.maze.Maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MazeMenu extends Menu {

    private static MazeMenu instance = null;
    protected Maze maze = null;

    public static MazeMenu getInstance(Maze maze) {
        if (instance == null) {
            instance = new MazeMenu();
        }
        instance.maze = maze;
        return instance;
    }

    @Override
    public void menu() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String name = "0";

            while (name.charAt(0) != '3') {
                menuText();
                name = reader.readLine();
                if (name.length() != 1) {
                    clearConsole();
                    System.out.println("Invalid option");
                } else {
                    switch (name.charAt(0)) {
                        case '1':
                            clearConsole();
                            System.out.println(maze.findShortestPath());
                            break;
                        case '2':
                            clearConsole();
                            System.out.println("Filename: ");
                            String file = reader.readLine();
                            maze.serializeMaze(file);
                            System.out.println("Serialized data is saved in " + file);
                            break;
                        case '3':
                            clearConsole();
                            continue;
                        default:
                            clearConsole();
                            System.out.println("Invalid option");
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            clearConsole();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            clearConsole();
            System.out.println(new UnknownError().getMessage());
        } catch (UnknownErrorException e) {
            clearConsole();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void menuText() {
        System.out.println("Your current maze:");
        System.out.print(maze);
        System.out.println("Please select an option:");
        System.out.println("1: Shortest path through maze");
        System.out.println("2: Save map");
        System.out.println("3: Back");
    }
}
