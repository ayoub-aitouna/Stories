package com.lastfidler.myapKLK.list;

public class Comment {
    String url;
    String id;
    String comment;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment() {
    }

    public Comment(String url, String id, String comment) {
        this.url = url;
        this.id = id;
        this.comment = comment;
    }


}
