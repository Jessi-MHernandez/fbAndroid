package com.example.gpstour;

public class CreateUser {
    public String name;
    public String email;
    public String password;
    public String code;

    public CreateUser(String name, String email, String password, String code, String issharing, String lat, String lng, String imageUri) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.issharing = issharing;
        this.lat = lat;
        this.lng = lng;
        this.imageUri = imageUri;
    }

    public String issharing;
    public String lat;
    public String lng;
    public String imageUri;

}
