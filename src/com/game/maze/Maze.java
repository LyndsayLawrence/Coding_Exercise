package com.game.maze;

import com.game.exceptions.InvalidMazeException;
import com.game.exceptions.UnknownErrorException;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Maze {
    private Room[][] maze;

    public Room[][] getMaze() {
        return maze;
    }

    public void setMaze(Room[][] maze) {
        this.maze = maze;
    }

    public int getLength() {
        if (maze != null) return maze.length;
        else return 0;
    }

    public int getHeight() {
        if (maze != null) return maze[0].length;
        else return 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.getLength(); ++i) {
            for (int j = 0; j < this.getHeight(); ++j) {
                stringBuilder.append(maze[i][j].getRoomContentCharacter());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;

        Maze objMaze = (Maze) obj;
        if (objMaze.getLength() != this.getLength() || objMaze.getHeight() != this.getHeight())
            return false;

        boolean equals = true;
        for (int i = 0; i < maze.length; ++i) {
            for (int j = 0; j < maze[i].length; ++j) {
                Room room = objMaze.getMaze()[i][j];
                Room thisRoom = this.getMaze()[i][j];
                if (thisRoom.position.x != room.position.x || thisRoom.position.y != room.position.y)
                    equals = false;
            }
        }
        return equals;
    }

    /**
     * Loads a maze from a text file.
     * EMPTY: '*'
     * MONSTER: 'm'
     * TREASURE: 't'
     * ENTRANCE: 'e'
     * EXIT: 'x'
     * BLOCKED: '_'
     *
     * @param filename String
     */
    public void loadMaze(String filename) throws UnknownErrorException, FileNotFoundException, InvalidMazeException {
        List<String> mazeStrings = new ArrayList<String>();

        try {
            FileReader in = new FileReader(filename);
            BufferedReader br = new BufferedReader(in);
            String line;

            while ((line = br.readLine()) != null) {
                mazeStrings.add(line);
            }

            convertArray(mazeStrings);

            in.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found.");
        } catch (IOException e) {
            throw new UnknownErrorException();
        }
    }


    /**
     * Serializes the maze to bytes and saves the file.
     *
     * @param filename String
     */
    public void serializeMaze(String filename) throws UnknownErrorException, FileNotFoundException {
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;

        try {
            fileOut = new FileOutputStream(filename);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(maze);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found.");
        } catch (IOException e) {
            throw new UnknownErrorException();
        } finally {
            try {
                if (out != null) out.close();
                if (fileOut != null) fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads the file and deserializes it into a maze.
     *
     * @param filename String
     */
    public void deserializeMaze(String filename) throws FileNotFoundException, UnknownErrorException, InvalidMazeException {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        Room[][] maze = null;

        try {
            fileIn = new FileInputStream(filename);
            in = new ObjectInputStream(fileIn);
            maze = (Room[][]) in.readObject();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found.");
        } catch (IOException e) {
            throw new UnknownErrorException();
        } catch (ClassNotFoundException e) {
            throw new InvalidMazeException("Invalid serialized maze.");
        } finally {
            try {
                if (in != null) in.close();
                if (fileIn != null) fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.maze = maze;
    }

    /**
     * Finds the shortest path from the entrance to the nearest exit.
     *
     * @return Path
     */
    public Path findShortestPath() {
        int[][] distanceMap = new int[maze.length][maze[0].length];
        Room start = null;
        List<Room> ends = new ArrayList<Room>();
        for (int i = 0; i < maze.length; ++i) {
            for (int j = 0; j < maze[0].length; ++j) {
                if (maze[i][j].getRoomContents() == Room.Contents.ENTRANCE)
                    start = maze[i][j];

                if (maze[i][j].getRoomContents() == Room.Contents.EXIT)
                    ends.add(maze[i][j]);
            }
        }

        if (ends.isEmpty()) return new Path(null);

        LinkedList<Room> queue = new LinkedList <Room>();
        queue.add(start);
        distanceMap[start.position.x][start.position.y] = 1;
        while (!queue.isEmpty()) {
            Room room = queue.poll();
            int level = distanceMap[room.position.x][room.position.y];
            Point[] nextCells = new Point[4];
            nextCells[3] = new Point(room.position.x, room.position.y - 1);
            nextCells[2] = new Point(room.position.x - 1, room.position.y);
            nextCells[1] = new Point(room.position.x, room.position.y + 1);
            nextCells[0] = new Point(room.position.x + 1, room.position.y);

            for (Point nextCell : nextCells)
            {
                if (nextCell.x < 0 || nextCell.y < 0) continue;
                if (nextCell.x == maze.length || nextCell.y == maze[0].length) continue;
                if (distanceMap[nextCell.x][nextCell.y] == 0
                        && maze[nextCell.x][nextCell.y].getRoomContents() != Room.Contents.BLOCKED) {
                    queue.add(maze[nextCell.x][nextCell.y]);
                    distanceMap[nextCell.x][nextCell.y] = level + 1;
                }
            }
        }
        int min = Integer.MAX_VALUE;
        Room closestEnd = null;
        for (Room end : ends) {
            if (distanceMap[end.position.x][end.position.y] != 0 && distanceMap[end.position.x][end.position.y] < min) {
                closestEnd = maze[end.position.x][end.position.y];
                min = distanceMap[end.position.x][end.position.y];
            }
        }

        if (closestEnd == null) return new Path(null);

        LinkedList <Room > path = new LinkedList <Room>();
        Room room = closestEnd;
        while (!room.equals(start)) {
            path.push(room);
            int level = distanceMap[room.position.x][room.position.y];
            Point[] nextCells = new Point[4];
            nextCells[3] = new Point(room.position.x, room.position.y - 1);
            nextCells[2] = new Point(room.position.x - 1, room.position.y);
            nextCells[1] = new Point(room.position.x, room.position.y + 1);
            nextCells[0] = new Point(room.position.x + 1, room.position.y);
            for (Point nextCell : nextCells) {
                if (nextCell.x < 0 || nextCell.y < 0)
                    continue;
                if (nextCell.x == maze.length
                        || nextCell.y == maze[0].length)
                    continue;
                if (distanceMap[nextCell.x][nextCell.y] == level - 1)
                {
                    room = maze[nextCell.x][nextCell.y];
                    break;
                }
            }
        }
        return new Path(path);
    }

    /**
     * Creates a random maze.
     */
    public void makeRandomMaze() {
        Point mazeSize = new Point((int) (Math.random() * 10.9) + 1, (int) (Math.random() * 10.9) + 1);
        Room[][] maze = new Room[mazeSize.x][mazeSize.y];
        for (int i = 0; i < maze.length; ++i) {
            for (int j = 0; j < maze[0].length; ++j) {
                int random = (int) (Math.random() * 3.9);
                switch (random) {
                    case 0:
                        maze[i][j] = new Room(i, j, Room.Contents.TREASURE);
                        break;
                    case 1:
                        maze[i][j] = new Room(i, j, Room.Contents.MONSTER);
                        break;
                    case 2:
                        maze[i][j] = new Room(i, j, Room.Contents.EMPTY);
                        break;
                    case 3:
                        maze[i][j] = new Room(i, j, Room.Contents.BLOCKED);
                        break;
                }
            }
        }
        int numberOfExits = (int) (Math.random() * 2.9);
        for (int i = 0; i < numberOfExits; ++i) {
            Point end = new Point((int) (Math.random() * (mazeSize.x - 0.1)), (int) (Math.random() * (mazeSize.y - 0.1)));
            maze[end.x][end.y] = new Room(end.x, end.y, Room.Contents.EXIT);
        }
        Point start = new Point((int) (Math.random() * (mazeSize.x - 0.1)), (int) (Math.random() * (mazeSize.y - 0.1)));
        maze[start.x][start.y] = new Room(start.x, start.y, Room.Contents.ENTRANCE);

        this.maze = maze;
    }

    /**
     * Converts a list of strings into a maze.
     * @param mazeStrings
     * @throws InvalidMazeException
     */
    protected void convertArray(List<String> mazeStrings) throws InvalidMazeException {
        if (mazeStrings.isEmpty()) throw new InvalidMazeException();

        maze = new Room[mazeStrings.size()][mazeStrings.get(0).length()];
        for (int i = 0; i < maze.length; ++i) {
            for (int j = 0; j < maze[0].length; ++j) {
                maze[i][j] = new Room(i, j, null);
                maze[i][j].setRoomContentsFromChar(mazeStrings.get(i).charAt(j));
            }
        }
    }
}
