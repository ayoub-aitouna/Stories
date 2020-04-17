package com.lastfidler.myapKLK.list;

public class user {
    private String name;
    private String email;
    private String id;
    private String url;

    public user() {
    }

    public user(String id, String name, String email, String url) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
