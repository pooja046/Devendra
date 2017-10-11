package com.DTO;

/**
 * Created by Mohit on 8/2/2017.
 */

public class MainDTO {


    private String email ;
    private String text2 ;
    private String text3 ;
    private String text4 ;
    private String text5 ;

    private String image ;

    public MainDTO() {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public MainDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getText2() {
        return text2;
    }

    public MainDTO setText2(String text2) {
        this.text2 = text2;
        return this;
    }

    public String getText3() {
        return text3;
    }

    public MainDTO setText3(String text3) {
        this.text3 = text3;
        return this;
    }

    public String getText4() {
        return text4;
    }

    public MainDTO setText4(String text4) {
        this.text4 = text4;
        return this;
    }

    public String getText5() {
        return text5;
    }

    public MainDTO setText5(String text5) {
        this.text5 = text5;
        return this;
    }

    public String getImage() {
        return image;
    }

    public MainDTO setImage(String image) {
        this.image = image;
        return this;
    }
}
