package com.example.jonathan.appciec.Models;

import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasenia;
    private ArrayList<String> favoritos;

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }


    public ArrayList<String> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(ArrayList<String> favoritos) {
        this.favoritos = favoritos;
    }

    public void agregarFavorito(String favorito){
        this.favoritos.add(favorito);
    }

    public void eliminarFavorito(String favorito){
        this.favoritos.add(favorito);
    }
}
