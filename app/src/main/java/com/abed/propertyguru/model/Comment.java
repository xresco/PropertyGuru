package com.abed.propertyguru.model;

import java.util.List;


public class Comment {

    private long id;
    private String by;
    private long time;
    private String text;
    private String type;
    private long parent;
    private long[] kids;


    public Comment() {
    }

    public Comment(long id, String by, long time, String text, String type, long parent, long[] kids) {
        this.id = id;
        this.by = by;
        this.time = time;
        this.text = text;
        this.type = type;
        this.parent = parent;
        this.kids = kids;
    }



    public String getBy() {
        return by;
    }

    public long getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }


    public long[] getKids() {
        return kids;
    }

}


