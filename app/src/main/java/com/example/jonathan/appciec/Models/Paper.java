package com.example.jonathan.appciec.Models;

public class Paper {
    private final String titulo;
    private final String articleLink;
    private final String autores;
    private final String fecha;
    private final String journal;
    private final String pais;
    private final String link_journalOverview;

    public Paper(String titulo, String articleLink, String autores, String fecha, String journal, String pais, String link_journalOverview) {
        this.titulo = titulo;
        this.articleLink = articleLink;
        this.autores = autores;
        this.fecha = fecha;
        this.link_journalOverview = link_journalOverview;
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
    public String getarticleLink() {
        return articleLink;
    }
    public String getLink_journalOverview() {
        return link_journalOverview;
    }
}


