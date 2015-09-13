package com.game.maze;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathTest {

    @Test
    public void testEquals() {
        Path path1 = createPath();
        Path path2 = createPath();
        assertTrue(path1.equals(path2));

        path2 = path1;
        assertTrue(path1.equals(path2));

        path2 = null;
        assertFalse(path1.equals(path2));

        Maze maze = new Maze();
        assertFalse(path1.equals(maze));
    }

    @Test
    public void testToString() {
        Path path = createPath();
        String expected = "(0,1),(1,1),(2,1),(3,1),";
        assertEquals(path.toString(), expected);

        path = new Path(null);
        expected = "No path possible";
        assertEquals(path.toString(), expected);
    }

    private Path createPath() {
        return new Path(Arrays.asList(new Room(0, 1, Room.Contents.TREASURE), new Room(1, 1, Room.Contents.TREASURE),
                new Room(2, 1, Room.Contents.MONSTER), new Room(3, 1, Room.Contents.EXIT)));
    }
}
