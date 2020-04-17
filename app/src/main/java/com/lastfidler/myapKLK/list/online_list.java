package com.lastfidler.myapKLK.list;

import android.net.Uri;

public class online_list {
    private String name;
    private String post;
    private String profile;
    private int likes;

    public online_list(String name, String post, String profile, int likes) {
        this.name = name;
        this.post = post;
        this.profile = profile;
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getPost() {
        return post;
    }

    public String getProfile() {
        return profile;
    }

    public int getLikes() {
        return likes;
    }
}
