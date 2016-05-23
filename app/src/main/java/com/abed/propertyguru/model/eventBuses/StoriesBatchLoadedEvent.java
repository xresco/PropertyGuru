package com.abed.propertyguru.model.eventBuses;

import com.abed.propertyguru.model.Story;

import java.util.List;


public class StoriesBatchLoadedEvent {

    private List<Story> stories;

    public StoriesBatchLoadedEvent(List<Story> strs) {
        stories = strs;
    }

    public List<Story> getStories() {
        return stories;
    }
}
