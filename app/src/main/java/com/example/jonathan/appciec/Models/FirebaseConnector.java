package com.example.jonathan.appciec.Models;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class FirebaseConnector {
    private DatabaseReference reff_user;

    public FirebaseConnector() {
        this.reff_user = FirebaseDatabase.getInstance().getReference().child("Usuario");
    }

    public DatabaseReference getReff_user() {
        return reff_user;
    }

    public void setReff_user(DatabaseReference reff_user) {
        this.reff_user = reff_user;
    }

    public void agregarFavorito(Paper paper, SessionHandler shandler, Map favoritos){
        DatabaseReference nuevo = this.reff_user.child(shandler.usuarioId()).child("Favoritos").push();
        nuevo.setValue(paper);
        favoritos.put(paper.getTitulo(),nuevo.getKey());
    }

    public void eliminarFavorito(Paper paper, SessionHandler shandler,Map favoritos){
        String id = (String) favoritos.get(paper.getTitulo());
        reff_user.child(shandler.usuarioId()).child("Favoritos").child(id).removeValue();
        favoritos.remove(paper.getTitulo());
    }
}
