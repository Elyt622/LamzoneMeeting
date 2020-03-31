package com.yohanbernole.lamzone.model;

import android.graphics.Color;

import java.util.Objects;

public class MeetingRoom {

    private long id;
    private String name;
    private int color;

    public MeetingRoom(long id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) { this.color = color; }

    public int getColor() { return color; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingRoom meetingRoom = (MeetingRoom) o;
        return Objects.equals(id, meetingRoom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
