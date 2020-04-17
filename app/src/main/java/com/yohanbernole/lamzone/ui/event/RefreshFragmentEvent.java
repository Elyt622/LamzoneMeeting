package com.yohanbernole.lamzone.ui.event;

public class RefreshFragmentEvent {

    private long id ;
    public RefreshFragmentEvent(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
