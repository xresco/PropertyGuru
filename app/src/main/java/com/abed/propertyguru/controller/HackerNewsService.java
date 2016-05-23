package com.abed.propertyguru.controller;

import com.abed.propertyguru.model.Comment;
import com.abed.propertyguru.model.Story;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface HackerNewsService {
    @GET("/v0/topstories.json")
    Observable<List<Long>> getTopStories();


    @GET("/v0/item/{story_id}.json")
    Observable<Story> getStory(@Path("story_id") long story_id);

    @GET("/v0/item/{comment_id}.json")
    Observable<Comment> getComment(@Path("comment_id") long comment_id);


}

