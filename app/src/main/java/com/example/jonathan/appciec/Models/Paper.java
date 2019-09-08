package com.example.jonathan.appciec.Models;

public class Paper {
    private final String titulo;
    private final String autores;
    private final String fecha;
    private final String journal;
    private final String pais;
    // --Commented out by Inspection (1/20/2019 9:16 PM):private String link_journalOverview;


    public Paper(String titulo, String autores, String fecha, String journal, String pais) {
        this.titulo = titulo;
        this.autores = autores;
        this.fecha = fecha;
//        String link1 = link;
        this.journal = journal;
        this.pais = pais;
    }


    //

    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
        return autores;
    }

    public String getFecha() {
        return fecha;
    }

    public String getJournal() {
        return journal;
    }

    public String getPais() {
        return pais;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "titulo='" + titulo + '\'' +
                ", autores='" + autores + '\'' +
                ", fecha='" + fecha + '\'' +
                ", journal='" + journal + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }

    //    public String getLink() {
//        return link;
//    }
//
//    public String getLink_journalOverview() {
//        return link_journalOverview;
//    }
}


