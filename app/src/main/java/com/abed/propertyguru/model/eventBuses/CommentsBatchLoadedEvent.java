package com.abed.propertyguru.model.eventBuses;

import com.abed.propertyguru.model.Comment;
import com.abed.propertyguru.model.Story;

import java.util.List;


public class CommentsBatchLoadedEvent {

    private List<Comment> comments;

    public CommentsBatchLoadedEvent(List<Comment> cmnts) {
        comments = cmnts;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
