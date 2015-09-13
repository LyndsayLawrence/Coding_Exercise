package com.game.maze;

import com.game.exceptions.InvalidMazeException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MazeTest {

    @Test
    public void testEquals() {
        Maze maze1 = createMazeWithTwoExits();
        Maze maze2 = createMazeWithTwoExits();
        assertEquals(maze1, maze2);

        maze2 = maze1;
        assertEquals(maze1, maze2);

        maze2 = createMazeWithOneExit();
        assertNotEquals(maze1, maze2);

        Path path = new Path(null);
        assertNotEquals(maze1, path);
    }

    @Test
    public void testToString() {
        Maze maze = createMazeWithTwoExits();
        String expected = "et\n" +
                          "_t\n" +
                          "xm\n" +
                          "*t\n" +
                          "_x\n";
        assertEquals(maze.toString(), expected);
    }

    @Test
    public void testConvertArrayWithValidData() {
        List<String> mazeStrings = Arrays.asList("et", "_t", "xm", "*t", "_x");
        Maze expected = createMazeWithTwoExits();
        Maze maze = new Maze();
        try {
            maze.convertArray(mazeStrings);
        } catch (InvalidMazeException e) {
            e.printStackTrace();
        }

        assertEquals(maze, expected);
    }


    @Test
    public void testConvertArrayWithInValidData() {
        List<String> mazeStrings = Arrays.asList("AA");
        Maze maze = new Maze();
        try {
            maze.convertArray(mazeStrings);
        } catch (InvalidMazeException e) {
            return;
        }
        fail("Expected: InvalidMazeException");
    }

    @Test
    public void testConvertArrayWithEmptyData() {
        List<String> mazeStrings = new ArrayList<String>();
        Maze maze = new Maze();
        try {
            maze.convertArray(mazeStrings);
        } catch (InvalidMazeException e) {
            return;
        }
        fail("Expected: InvalidMazeException");
    }

    @Test
    public void testFindShortestPathWithZeroExits() {
        Maze maze = createMazeWithZeroExits();
        Path result = maze.findShortestPath();

        assertTrue(result.getPath() == null);
    }

    @Test
    public void testFindShortestPathWithOneExit() {
        Maze maze = createMazeWithOneExit();
        Path expected = new Path(Arrays.asList(new Room(0,1, Room.Contents.TREASURE), new Room(1,1, Room.Contents.TREASURE),
                new Room(2,1, Room.Contents.TREASURE), new Room(3,1, Room.Contents.EXIT)));
        Path result = maze.findShortestPath();

        assertEquals(expected, result);
    }

    @Test
    public void testFindShortestPathWithTwoExits() {
        Maze maze = createMazeWithTwoExits();
        Path expected = new Path(Arrays.asList(new Room(0,1, Room.Contents.TREASURE), new Room(1,1, Room.Contents.TREASURE),
                new Room(2,1, Room.Contents.MONSTER), new Room(2,0, Room.Contents.EXIT)));
        Path result = maze.findShortestPath();

        assertEquals(expected, result);
    }

    private Maze createMazeWithTwoExits() {
        Maze maze = new Maze();
        maze.setMaze(new Room[][]{
                {new Room(0,0, Room.Contents.ENTRANCE), new Room(0,1, Room.Contents.TREASURE)},
                {new Room(1,0, Room.Contents.BLOCKED), new Room(1,1, Room.Contents.TREASURE)},
                {new Room(2,0, Room.Contents.EXIT), new Room(2,1, Room.Contents.MONSTER)},
                {new Room(3,0, Room.Contents.EMPTY), new Room(3,1, Room.Contents.TREASURE)},
                {new Room(4,0, Room.Contents.BLOCKED), new Room(4,1, Room.Contents.EXIT)}
        });
        return maze;
    }

    private Maze createMazeWithOneExit() {
        Maze maze = new Maze();
        maze.setMaze(new Room[][]{
                {new Room(0,0, Room.Contents.ENTRANCE), new Room(0,1, Room.Contents.TREASURE)},
                {new Room(1,0, Room.Contents.BLOCKED), new Room(1,1, Room.Contents.TREASURE)},
                {new Room(2,0, Room.Contents.EMPTY), new Room(2,1, Room.Contents.TREASURE)},
                {new Room(3,0, Room.Contents.BLOCKED), new Room(3,1, Room.Contents.EXIT)}
        });
        return maze;
    }

    private Maze createMazeWithZeroExits() {
        Maze maze = new Maze();
        maze.setMaze(new Room[][]{
                {new Room(0,0, Room.Contents.ENTRANCE), new Room(0,1, Room.Contents.TREASURE)},
                {new Room(1,0, Room.Contents.BLOCKED), new Room(1,1, Room.Contents.TREASURE)},
                {new Room(2,0, Room.Contents.EMPTY), new Room(2,1, Room.Contents.TREASURE)}
        });
        return maze;
    }
}