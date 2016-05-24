package com.abed.propertyguru;


import android.support.annotation.NonNull;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abed.propertyguru.controller.RxBus;
import com.abed.propertyguru.model.Story;
import com.abed.propertyguru.model.eventBuses.StoriesBatchLoadedEvent;
import com.abed.propertyguru.view.activity.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickingStoryItemShouldOpenCommentScreen() {

        List<Story> stories = giveStories(5);
        RxBus.getInstance().postOnTheUiThread(new StoriesBatchLoadedEvent(stories));
        onView(withId(R.id.rvItems))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


    }


    public List<Story> giveStories(int size) {
        List<Story> stories = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            stories.add(new Story(i, "by" + i, i, "title " + i, "type " + i, "url " + i, givenKids(i * size, 4)));
        }
        return stories;
    }


    public long[] givenKids(int start_value, int size) {
        long[] kids = new long[size];
        for (int i = 0; i < size; i++) {
            kids[i] = i + start_value;
        }
        return kids;
    }




}
