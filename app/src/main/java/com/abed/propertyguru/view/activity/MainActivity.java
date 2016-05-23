package com.abed.propertyguru.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.abed.propertyguru.R;
import com.abed.propertyguru.controller.API_Utils;
import com.abed.propertyguru.controller.RxBus;
import com.abed.propertyguru.model.Story;
import com.abed.propertyguru.model.eventBuses.StoriesBatchLoadedEvent;
import com.abed.propertyguru.model.eventBuses.StoriesLoadedEvent;
import com.abed.propertyguru.view.adapter.StoriesAdapter;
import com.abed.propertyguru.view.misc.VerticalEqualSpaceItemDecoration;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

public class MainActivity extends AppCompatActivity implements StoriesAdapter.ViewHolderClicks {
    private final String TAG = getClass().getName();
    private Subscription storiesLoadingBusSubscription;
    private Subscription storiesBatchLoadingBusSubscription;

    private List<Long> stories_ids;

    private StoriesAdapter storiesAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        initRx();
        API_Utils.loadStories();
    }

    private void loadViews() {
        storiesAdapter = new StoriesAdapter(new LinkedList<>(), this);
        recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setAdapter(storiesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new VerticalEqualSpaceItemDecoration(2));


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            storiesAdapter.clear();
            API_Utils.loadStoriesBatch(stories_ids.subList(0, 10));
        });

        swipeContainer.post(() -> swipeContainer.setRefreshing(true));


    }

    private void initRx() {
        storiesLoadingBusSubscription = RxBus.getInstance()
                .register(StoriesLoadedEvent.class,
                        event -> {
                            stories_ids = event.getStoriesIds();
                            API_Utils.loadStoriesBatch(event.getStoriesIds().subList(0, 10)); // load only the top ten

                        });
        storiesBatchLoadingBusSubscription = RxBus.getInstance()
                .register(StoriesBatchLoadedEvent.class,
                        event -> {
                            storiesAdapter.addToList(event.getStories());
                            swipeContainer.setRefreshing(false);
                        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        storiesLoadingBusSubscription.unsubscribe();
        storiesBatchLoadingBusSubscription.unsubscribe();
    }

    @Override
    public void onStorySelected(View view, int position, Story story) {
        Log.d(TAG, story.getTitle());
        CommentsActivity.startActivity(this,story.getKids());
    }
}
