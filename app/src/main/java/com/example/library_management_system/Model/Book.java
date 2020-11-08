package com.example.library_management_system.Model;

import java.io.Serializable;

public class Book implements Serializable {
    String Name,Location,Author,bId,Available,ImageUrl;

    public Book() {
    }

    public Book(String name, String location, String author, String bId, String available, String imageUrl) {
        Name = name;
        Location = location;
        Author = author;
        this.bId = bId;
        Available = available;
        ImageUrl = imageUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String available) {
        Available = available;
    }
}
