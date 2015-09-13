package com.game.maze;

import com.game.exceptions.InvalidMazeException;

import java.awt.Point;
import java.io.Serializable;

public class Room implements Serializable{
    Contents roomContents;
    Point position;


    enum Contents {
        EMPTY(),
        MONSTER,
        TREASURE,
        ENTRANCE,
        EXIT,
        BLOCKED
    }

    public Room(int row, int col, Contents contents)
    {
        position = new Point(row, col);
        this.roomContents = contents;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        Room room = (Room) obj;
        if (position.x == room.position.x && position.y == room.position.y && roomContents.equals(room.roomContents))
            return true;
        return false;
    }

    @Override
    public String toString()
    {
        return "(" + position.x + "," + position.y + ")";
    }

    /**
     * Gets the character representation of thr room contents.
     * EMPTY: '*'
     * MONSTER: 'm'
     * TREASURE: 't'
     * ENTRANCE: 'e'
     * EXIT: 'x'
     * BLOCKED: '_'
     *
     * @return char
     */
    public char getRoomContentCharacter() {
        switch (roomContents) {
            case EMPTY: return '*';
            case MONSTER: return 'm';
            case TREASURE: return 't';
            case ENTRANCE: return 'e';
            case EXIT: return 'x';
            case BLOCKED: return '_';
            default: return '_';
        }
    }

    public Contents getRoomContents() {
        return roomContents;
    }

    public void setRoomContents(Contents roomContents) {
        this.roomContents = roomContents;
    }

    /**
     * Sets the room contents based on the given character.
     * EMPTY: '*'
     * MONSTER: 'm'
     * TREASURE: 't'
     * ENTRANCE: 'e'
     * EXIT: 'x'
     * BLOCKED: '_'
     *
     * @param roomContents char
     * @throws InvalidMazeException
     */
    public void setRoomContentsFromChar(char roomContents) throws InvalidMazeException {
        switch (roomContents) {
            case '*':
                this.roomContents = Contents.EMPTY;
                break;
            case 'm':
                this.roomContents = Contents.MONSTER;
                break;
            case 't':
                this.roomContents = Contents.TREASURE;
                break;
            case 'e':
                this.roomContents = Contents.ENTRANCE;
                break;
            case 'x':
                this.roomContents = Contents.EXIT;
                break;
            case '_':
                this.roomContents = Contents.BLOCKED;
                break;
            default:
                throw new InvalidMazeException();
        }
    }
}
