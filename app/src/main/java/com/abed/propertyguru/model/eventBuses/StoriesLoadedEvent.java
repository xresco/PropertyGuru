package com.abed.propertyguru.model.eventBuses;

import java.util.List;


public class StoriesLoadedEvent {

    private List<Long> stories_ids;

    public StoriesLoadedEvent(List<Long> ids) {
        stories_ids = ids;
    }

    public List<Long> getStoriesIds() {
        return stories_ids;
    }
}
