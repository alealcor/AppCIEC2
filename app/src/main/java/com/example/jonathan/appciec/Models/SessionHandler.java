package com.example.jonathan.appciec.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionHandler {
    private final Context mContext;

    public SessionHandler(Context context) {
        this.mContext = context;
    }

    public boolean estaIniciado(){
        SharedPreferences preferencias = mContext.getSharedPreferences("Credenciales.sesion",Context.MODE_PRIVATE);
        return preferencias.getBoolean("log",false);
    }

    public String usuarioId(){
        SharedPreferences preferencias = mContext.getSharedPreferences("Credenciales.sesion",Context.MODE_PRIVATE);
        return preferencias.getString("token","No esta registrado");
    }

    public String usuarioCorreo(){
        SharedPreferences preferencias = mContext.getSharedPreferences("Credenciales.sesion",Context.MODE_PRIVATE);
        return preferencias.getString("correo","No esta registrado");
    }
    public boolean guardarSesion(String correo, String token){
        try {
            SharedPreferences preferencias = mContext.getSharedPreferences("Credenciales.sesion",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("correo",correo);
            editor.putString("token",token);
            editor.putBoolean("log",true);
            editor.commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void cerrarSesion(){
        SharedPreferences preferences = mContext.getSharedPreferences("Credenciales.sesion", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("log",false).apply();
        preferences.edit().putString("correo",null).apply();
        preferences.edit().putString("token",null).apply();
    }
}
