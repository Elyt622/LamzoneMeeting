package com.yohanbernole.lamzone.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Meeting {

    private long id;
    private String name;
    private Date hours;
    private Duration duration;
    private MeetingRoom room;
    private String subject;
    private ArrayList<User> users;

    public Meeting(long id, String name, Date hours, MeetingRoom room, String subject, ArrayList<User> users) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.room = room;
        this.subject = subject;
        this.users = users;
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

    public MeetingRoom getLocation() {
        return room;
    }

    public void setLocation(MeetingRoom room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<User> getUsers() { return users; }

    public void setUsers(ArrayList<User> users) { this.users = users; }

    public void setHours(Date hours) { this.hours = hours; }

    public Date getHours() { return hours; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
