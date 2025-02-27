package com.yohanbernole.lamzone.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Meeting {

    private long id;
    private String name;
    private Date date;
    private int duration;
    private MeetingRoom room;
    private String subject;
    private List<User> users;

    public Meeting(long id, String name, Date date, MeetingRoom room, String subject, List<User> users, int duration) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.room = room;
        this.subject = subject;
        this.users = users;
        this.duration = duration;
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

    public List<User> getUsers() { return users; }

    public void setUsers(List<User> users) { this.users = users; }

    public void setHours(Date date) { this.date = date; }

    public Date getDate() { return date; }

    public void setDuration(int duration) { this.duration = duration; }

    public int getDuration() { return duration; }

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
