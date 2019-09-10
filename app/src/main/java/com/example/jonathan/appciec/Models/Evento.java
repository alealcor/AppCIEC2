package com.example.jonathan.appciec.Models;

public class Evento {
    private final String img_url;

    public Evento(String img_url) {
        this.img_url = img_url;
    }

    public String getImageResource() {
        return img_url;
    }
}
