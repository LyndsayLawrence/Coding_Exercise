package com.game.menu;

import com.game.exceptions.InvalidMazeException;
import com.game.exceptions.UnknownErrorException;
import com.game.maze.Maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMenu extends Menu {
    private static MainMenu instance = null;
    private Maze maze = new Maze();

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    @Override
    public void menu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = "0";
        clearConsole();

        while (name.charAt(0) != '4') {
            try {
                menuText();
                name = reader.readLine();
                if (name.length() != 1) {
                    clearConsole();
                    System.out.println("Invalid option");
                } else {
                    switch (name.charAt(0)) {
                        case '1':
                            maze.makeRandomMaze();
                            clearConsole();
                            MazeMenu.getInstance(maze).menu();
                            break;
                        case '2':
                            System.out.println("File: ");
                            String file = reader.readLine();
                            maze.loadMaze(file);
                            clearConsole();
                            MazeMenu.getInstance(maze).menu();
                            break;
                        case '3':
                            System.out.println("File: ");
                            String serializedFile = reader.readLine();
                            maze.deserializeMaze(serializedFile);
                            clearConsole();
                            MazeMenu.getInstance(maze).menu();
                            break;
                        case '4':
                            continue;
                        default:
                            clearConsole();
                            System.out.println("Invalid option");
                            break;
                    }
                }
            } catch (UnknownErrorException e) {
                clearConsole();
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                clearConsole();
                System.out.println(e.getMessage());
            } catch(IOException e){
                clearConsole();
                System.out.println(new UnknownError().getMessage());
            } catch (InvalidMazeException e) {
                clearConsole();
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void menuText() {
        System.out.println("Welcome to Lyndsay's maze solver!");
        System.out.println("Please select an option:");
                System.out.println("1: Generate random maze");
                System.out.println("2: Load existing maze");
                System.out.println("3: Load serialized map");
                System.out.println("4: Exit");
    }
}
