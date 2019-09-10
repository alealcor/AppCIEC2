package com.example.jonathan.appciec.Models;

public class Noticia {
    private final String titulo;
    private final String contenido;
    private final String imageResource;

    public Noticia(String titulo, String contenido, String imageResource) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.imageResource = imageResource;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getImageResource() {
        return imageResource;
    }
}


