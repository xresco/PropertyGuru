package com.abed.propertyguru.model;


public class Story {
    private long id;
    private String by;
    private long time;
    private String title;
    private String type;
    private String url;
    private long[] kids;


    public Story() {
    }

    public Story(long id, String by, long time, String title, String type, String url, long[] kids) {
        this.id = id;
        this.by = by;
        this.time = time;
        this.title = title;
        this.type = type;
        this.url = url;
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

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public long[] getKids() {
        return kids;
    }

}
