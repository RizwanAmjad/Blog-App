package com.rizwanamjadnov.blog.Model;

public class Blog {
    private String title;
    private String description;
    private String image;
    private String timeStamp;
    private String userId;

    public Blog() {
    }

    public Blog(String title, String description, String image, String timeStamp, String userId) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.timeStamp = timeStamp;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
