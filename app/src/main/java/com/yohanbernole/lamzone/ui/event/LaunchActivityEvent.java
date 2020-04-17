package com.yohanbernole.lamzone.ui.event;

public class LaunchActivityEvent {
    private long id ;
    public LaunchActivityEvent(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
