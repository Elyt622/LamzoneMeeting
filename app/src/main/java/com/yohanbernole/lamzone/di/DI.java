package com.yohanbernole.lamzone.di;


import com.yohanbernole.lamzone.service.DummyMeetingApiService;
import com.yohanbernole.lamzone.service.MeetingApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}
