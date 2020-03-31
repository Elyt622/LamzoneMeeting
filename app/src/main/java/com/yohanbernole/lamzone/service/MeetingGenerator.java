package com.yohanbernole.lamzone.service;

import android.graphics.Color;

import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.MeetingRoom;
import com.yohanbernole.lamzone.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class MeetingGenerator {

    public static List<User> USERS = Arrays.asList(
            new User(1, "Yohan", "yohan.bernole@gmail.com"),
            new User(2, "Joseph", "joseph.dupond@gmail.com"),
            new User(3, "Nathan", "nathan.truc@gmail.com"),
            new User(4, "Céline", "céline.nom@gmail.com"),
            new User(5, "Yann", "yann.chose@gmail.com"),
            new User(6, "Charlotte", "charlotte.truc@gmail.com"),
            new User(7, "John", "john.doe@gmail.com"),
            new User(8, "Jane", "jane.doe@gmail.com"),
            new User(9, "Franck", "franck.email@gmail.com")
            );

    public static List<MeetingRoom> MEETINGROOMS = Arrays.asList(
            new MeetingRoom(1, "A1", Color.GRAY),
            new MeetingRoom(2, "A2", Color.BLUE),
            new MeetingRoom(3, "A3", Color.YELLOW),
            new MeetingRoom(4, "E1", Color.CYAN),
            new MeetingRoom(5, "E2", Color.RED),
            new MeetingRoom(6, "E3", Color.DKGRAY),
            new MeetingRoom(7, "M1", Color.GREEN),
            new MeetingRoom(8, "M2", Color.MAGENTA),
            new MeetingRoom(9, "M3", Color.BLACK),
            new MeetingRoom(10, "M4", Color.LTGRAY)
    );

    public static List<Meeting> MEETINGS = Arrays.asList(
                        new Meeting(1, "Reunion hebdo", new Date(), MEETINGROOMS.get(1), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(3)))),
                        new Meeting(2, "Reunion 1", new Date(), MEETINGROOMS.get(2), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(3, "Reunion 2", new Date() ,MEETINGROOMS.get(4), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(4, "Reunion 3", new Date() ,MEETINGROOMS.get(9), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(3), USERS.get(6)))),
                        new Meeting(5, "Reunion 4", new Date() ,MEETINGROOMS.get(1), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(6, "Reunion 5", new Date() ,MEETINGROOMS.get(5), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(5), USERS.get(3)))),
                        new Meeting(7, "Reunion 6", new Date(),MEETINGROOMS.get(8), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(8, "Reunion 7", new Date(),MEETINGROOMS.get(7), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(9, "Reunion 8", new Date(),MEETINGROOMS.get(6), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(10, "Reunion 9", new Date(),MEETINGROOMS.get(2), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(11, "Reunion 10", new Date(),MEETINGROOMS.get(3), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(12, "Reunion 11", new Date(),MEETINGROOMS.get(9), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(13, "Reunion 12", new Date(),MEETINGROOMS.get(6), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(14, "Reunion 13", new Date(),MEETINGROOMS.get(8), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(15, "Reunion 14", new Date(),MEETINGROOMS.get(4), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2)))),
                        new Meeting(16, "Reunion 15", new Date(),MEETINGROOMS.get(3), "Bilan de l'année", new ArrayList<>(Arrays.asList(USERS.get(1), USERS.get(2))))
                );

    protected MeetingGenerator() throws ParseException, ParseException {
    }

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGS);
    }

    static List<MeetingRoom> generateMeetingRooms() {
        return new ArrayList<>(MEETINGROOMS);
    }

}
