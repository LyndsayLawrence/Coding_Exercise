package com.game.maze;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Room> path = new ArrayList<Room>();

    public Path(List<Room> path) {
        this.path = path;
    }

    public List<Room> getPath() {
        return path;
    }

    public void setPath(List<Room> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        if (path == null) {
            return "No path possible";
        }
        String pathString = "";
        for (Room cell : path) {
            pathString = pathString + cell + ",";
        }
        return pathString;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        if (((Path) obj).getPath().equals(this.getPath()))
            return true;
        return false;
    }
}
