package com.example.library_management_system.Model;

import java.io.Serializable;

public class IssueRequest implements Serializable {
    String ImageUrl,authorName,bId,bookName,numDays,uId,userEmail;

    public IssueRequest(String imageUrl, String authorName, String bId, String bookName, String numDays, String uId, String userEmail) {
        ImageUrl = imageUrl;
        this.authorName = authorName;
        this.bId = bId;
        this.bookName = bookName;
        this.numDays = numDays;
        this.uId = uId;
        this.userEmail = userEmail;
    }

    public IssueRequest() {
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getNumDays() {
        return numDays;
    }

    public void setNumDays(String numDays) {
        this.numDays = numDays;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
