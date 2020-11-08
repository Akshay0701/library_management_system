package com.example.library_management_system.Model;

public class IssuedBook {
    String ImageUrl,authorName,bId,bookName,dueDate,issuedDate,uId,userEmail;

    public IssuedBook(String imageUrl, String authorName, String bId, String bookName, String dueDate, String issuedDate, String uId, String userEmail) {
        ImageUrl = imageUrl;
        this.authorName = authorName;
        this.bId = bId;
        this.bookName = bookName;
        this.dueDate = dueDate;
        this.issuedDate = issuedDate;
        this.uId = uId;
        this.userEmail = userEmail;
    }

    public IssuedBook() {
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
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
