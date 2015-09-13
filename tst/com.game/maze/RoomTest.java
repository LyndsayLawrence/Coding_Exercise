package com.game.maze;

import com.game.exceptions.InvalidMazeException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RoomTest {

    @Test
    public void testEquals() {
        Room room1 = new Room(0, 0, Room.Contents.TREASURE);
        Room room2 = new Room(0, 0, Room.Contents.TREASURE);
        assertTrue(room1.equals(room2));

        room2 = room1;
        assertTrue(room1.equals(room2));

        room2 = null;
        assertFalse(room1.equals(room2));

        room2 = new Room(0, 1, Room.Contents.TREASURE);
        assertFalse(room1.equals(room2));

        room2 = new Room(1, 0, Room.Contents.TREASURE);
        assertFalse(room1.equals(room2));

        room2 = new Room(0, 0, Room.Contents.MONSTER);
        assertFalse(room1.equals(room2));

        Path path = new Path(null);
        assertFalse(room1.equals(path));
    }

    @Test
    public void testRoomContentsFromChar() throws InvalidMazeException {
        Room room = new Room(0, 0, null);
        room.setRoomContentsFromChar('*');
        assertEquals(room.getRoomContents(), Room.Contents.EMPTY);
        room.setRoomContentsFromChar('m');
        assertEquals(room.getRoomContents(), Room.Contents.MONSTER);
        room.setRoomContentsFromChar('t');
        assertEquals(room.getRoomContents(), Room.Contents.TREASURE);
        room.setRoomContentsFromChar('e');
        assertEquals(room.getRoomContents(), Room.Contents.ENTRANCE);
        room.setRoomContentsFromChar('x');
        assertEquals(room.getRoomContents(), Room.Contents.EXIT);
        room.setRoomContentsFromChar('_');
        assertEquals(room.getRoomContents(), Room.Contents.BLOCKED);
    }

    @Test
    public void testRoomContentsFromCharError() {
        Room room = new Room(0, 0, null);
        try {
            room.setRoomContentsFromChar('A');
        } catch (InvalidMazeException e) {
            return;
        }
        fail("Expected: InvalidMazeException");
    }
}
