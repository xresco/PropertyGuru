package com.abed.propertyguru.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abed.propertyguru.R;
import com.abed.propertyguru.model.Comment;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CustomViewHolder> {
    private List<Comment> comments;
    private ViewHolderClicks clicksListener;

    public CommentsAdapter(List<Comment> comments,
                           ViewHolderClicks clicksListener) {
        this.comments = comments;
        this.clicksListener = clicksListener;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new CustomViewHolder(view, clicksListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (comments == null || comments.size() == 0) {
            return;
        }
        holder.setComment(comments.get(position));

    }

    @Override
    public int getItemCount() {
        if (comments == null) {
            return 0;
        }

        return comments.size();
    }

    public void updateList(List<Comment> itemsList) {
        this.comments = itemsList;
        notifyDataSetChanged();
    }

    public void addToList(List<Comment> itemsList) {
        this.comments.addAll(itemsList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.comments.clear();
        notifyDataSetChanged();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView txt_body;
        ViewHolderClicks clicksListener;
        Comment comment;

        public CustomViewHolder(View view, ViewHolderClicks clicksListener) {
            super(view);
            this.clicksListener = clicksListener;
            txt_body = (TextView) view.findViewById(R.id.txt_body);
            view.setOnClickListener(this);
        }


        public void setComment(Comment comment) {
            this.comment = comment;
            try {
                txt_body.setText(Html.fromHtml(comment.getText()));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            if (clicksListener != null)
                this.clicksListener.onCommentSelected(v, getLayoutPosition(), comment);
        }
    }


    public interface ViewHolderClicks {
        void onCommentSelected(View view, int position, Comment comment);
    }
}
