package com.example.library_management_system.Model;

public class RequestNewBook {
    String Author,Name,uId;

    public RequestNewBook() {
    }

    public RequestNewBook(String author, String name, String uId) {
        Author = author;
        Name = name;
        this.uId = uId;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
