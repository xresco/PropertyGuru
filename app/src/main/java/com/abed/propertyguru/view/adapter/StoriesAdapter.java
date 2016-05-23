package com.abed.propertyguru.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abed.propertyguru.R;
import com.abed.propertyguru.model.Story;

import java.util.List;


public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.CustomViewHolder> {
    private List<Story> stories;
    private ViewHolderClicks clicksListener;

    public StoriesAdapter(List<Story> stories,
                          ViewHolderClicks clicksListener) {
        this.stories = stories;
        this.clicksListener = clicksListener;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new CustomViewHolder(view, clicksListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (stories == null || stories.size() == 0) {
            return;
        }
        holder.setStory(stories.get(position));

    }

    @Override
    public int getItemCount() {
        if (stories == null) {
            return 0;
        }

        return stories.size();
    }

    public void updateList(List<Story> itemsList) {
        this.stories = itemsList;
        notifyDataSetChanged();
    }

    public void addToList(List<Story> itemsList) {
        this.stories.addAll(itemsList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.stories.clear();
        notifyDataSetChanged();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView txt_body;
        ViewHolderClicks clicksListener;
        Story story;

        public CustomViewHolder(View view, ViewHolderClicks clicksListener) {
            super(view);
            this.clicksListener = clicksListener;
            txt_body = (TextView) view.findViewById(R.id.txt_body);
            view.setOnClickListener(this);
        }


        public void setStory(Story story) {
            this.story = story;
            txt_body.setText(story.getTitle());
        }

        @Override
        public void onClick(View v) {
            if (clicksListener != null)
                this.clicksListener.onStorySelected(v, getLayoutPosition(), story);
        }
    }


    public interface ViewHolderClicks {
        void onStorySelected(View view, int position, Story story);
    }
}
