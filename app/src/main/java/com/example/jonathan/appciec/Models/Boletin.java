package com.example.jonathan.appciec.Models;

public class Boletin {
    private final String informacion;
    private final String url_pdf;
    private final String url_img;

    public Boletin(String informacion, String url_pdf, String url_img) {
        this.informacion = informacion;
        this.url_pdf = url_pdf;
        this.url_img = url_img;
    }

    public String getInformacion() {
        return informacion;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }

    public String getUrl_img() {
        return url_img;
    }

}