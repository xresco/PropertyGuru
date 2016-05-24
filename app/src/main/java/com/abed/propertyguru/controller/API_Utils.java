package com.abed.propertyguru.controller;


import android.util.Log;

import com.abed.propertyguru.BuildConfig;
import com.abed.propertyguru.model.Comment;
import com.abed.propertyguru.model.Story;
import com.abed.propertyguru.model.eventBuses.CommentsBatchLoadedEvent;
import com.abed.propertyguru.model.eventBuses.StoriesBatchLoadedEvent;
import com.abed.propertyguru.model.eventBuses.StoriesLoadedEvent;

import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class API_Utils {
    private String TAG = getClass().getName();

    public static void loadStories() {
        new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_URL)
                .build()
                .create(HackerNewsService.class)
                .getTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Long>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            loadStories();
                        }
                    }

                    @Override
                    public void onNext(List<Long> stories_ids) {
                        RxBus.getInstance().postOnTheUiThread(new StoriesLoadedEvent(stories_ids));
                    }
                });
    }

    public static void loadStoriesBatch(List<Long> ids) {
        Observable.just(ids)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .flatMap(id -> loadStory(id)) // Calls the method which returns a new Observable<Item>
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Story>() {
                    List<Story> stories;

                    @Override
                    public void onCompleted() {
                        Log.d("RX", "onCompleted: ");
                        RxBus.getInstance().postOnTheUiThread(new StoriesBatchLoadedEvent(stories));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        stories = new LinkedList<Story>();
                    }

                    @Override
                    public void onNext(Story s) {
                        Log.d("story::", s.getTitle());
                        stories.add(s);
                    }

                });
    }


    private static Observable<Story> loadStory(final long id) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_URL)
                .build()
                .create(HackerNewsService.class)
                .getStory(id);

    }


    public static void loadCommentsBatch(List<Long> ids) {
        Observable.just(ids)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .flatMap(id -> loadComment(id)) // Calls the method which returns a new Observable<Item>
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Comment>() {
                    List<Comment> comments;

                    @Override
                    public void onCompleted() {
                        Log.d("RX", "onCompleted: ");
                        RxBus.getInstance().postOnTheUiThread(new CommentsBatchLoadedEvent(comments));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        comments = new LinkedList<Comment>();
                    }

                    @Override
                    public void onNext(Comment s) {
                        comments.add(s);
                    }

                });
    }

    public static Observable<Comment> loadComment(final long id) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_BASE_URL)
                .build()
                .create(HackerNewsService.class)
                .getComment(id);
    }


}