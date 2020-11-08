package com.example.library_management_system.Model;

public class User {
    String Name,Email,Phone,Password,RollId,uid;

    public User() {
    }

    public User(String name, String email, String phone, String password, String rollId, String uid) {
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
        RollId = rollId;
        this.uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRollId() {
        return RollId;
    }

    public void setRollId(String rollId) {
        RollId = rollId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
