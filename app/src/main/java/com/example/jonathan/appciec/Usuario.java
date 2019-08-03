package com.example.jonathan.appciec;

public class Usuario {
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasenia;
    private String comprobarContra;

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

    public String getComprobarContra() {
        return comprobarContra;
    }

    public void setComprobarContra(String comprobarContra) {
        this.comprobarContra = comprobarContra;
    }
}
