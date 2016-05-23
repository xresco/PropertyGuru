package com.abed.propertyguru.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abed.propertyguru.R;
import com.abed.propertyguru.controller.API_Utils;
import com.abed.propertyguru.controller.RxBus;
import com.abed.propertyguru.model.Comment;
import com.abed.propertyguru.model.Story;
import com.abed.propertyguru.model.eventBuses.CommentsBatchLoadedEvent;
import com.abed.propertyguru.model.eventBuses.StoriesBatchLoadedEvent;
import com.abed.propertyguru.model.eventBuses.StoriesLoadedEvent;
import com.abed.propertyguru.view.adapter.CommentsAdapter;
import com.abed.propertyguru.view.adapter.StoriesAdapter;
import com.abed.propertyguru.view.misc.VerticalEqualSpaceItemDecoration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

public class CommentsActivity extends AppCompatActivity implements CommentsAdapter.ViewHolderClicks {
    private final String TAG = getClass().getName();
    private Subscription commentsBatchLoadingBusSubscription;

    private List<Long> comments_ids;

    private CommentsAdapter commentsAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;

    private static final String COMMENTS_ID = "comments id";

    public static void startActivity(Context context, long[] comments_id) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(COMMENTS_ID, comments_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long[] comments= getIntent().getLongArrayExtra(COMMENTS_ID);
        comments_ids=new LinkedList<>();
        if(comments!=null) {
            for (int index = 0; index < comments.length; index++) {
                comments_ids.add(comments[index]);
            }
        }else {
            Toast.makeText(this,"No Comments",Toast.LENGTH_SHORT).show();
        }
        loadViews();
        initRx();
        API_Utils.loadCommentsBatch(comments_ids);
    }

    private void loadViews() {
        commentsAdapter = new CommentsAdapter(new LinkedList<>(), this);
        recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new VerticalEqualSpaceItemDecoration(2));


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            commentsAdapter.clear();
            API_Utils.loadCommentsBatch(comments_ids);
        });

        swipeContainer.post(() -> swipeContainer.setRefreshing(true));


    }

    private void initRx() {
        commentsBatchLoadingBusSubscription = RxBus.getInstance()
                .register(CommentsBatchLoadedEvent.class,
                        event -> {
                            commentsAdapter.addToList(event.getComments());
                            swipeContainer.setRefreshing(false);
                        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        commentsBatchLoadingBusSubscription.unsubscribe();
    }


    @Override
    public void onCommentSelected(View view, int position, Comment comment) {

    }

}

