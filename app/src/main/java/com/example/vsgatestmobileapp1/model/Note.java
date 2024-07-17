package com.example.vsgatestmobileapp1.model;

import android.content.ContentValues;

public class Note {
    private long id;
    private String title;
    private String subtitle;
    private String content;
    private String imageUrl;
    private String dateCreated;
    private String dateUpdated;
    private String username;

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ContentValues toContentValues(Note note) {
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("subtitle", note.getSubtitle());
        values.put("content", note.getContent());
        values.put("image_url", note.getImageUrl());
        values.put("date_created", note.getDateCreated());
        values.put("date_updated", note.getDateUpdated());
        values.put("username", note.getUsername());
        return values;
    }
}
