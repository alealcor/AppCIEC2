package com.example.jonathan.appciec;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MetodosdePrueba {

    MetodosdePrueba(){

    }
    //Metodo del Login
    public boolean validarUsuarioLogin(String correo, String contra) {
        if (correo.length()==0) {
            //Toast.makeText(InicioSesionActivity,"Se debe ingresar correo",Toast.LENGTH_LONG).show();
            return false;
        } else if (correo.length() > 0) {
            boolean emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").matcher(correo).matches();
            return emailPattern;
        }else if (contra.length() == 0 ) {
            return false;
        }else if (contra.length() < 6 ) {
            return false;
        }
        return false;

    }

    //Método del Registro
    public boolean validarUsuarioRegistro(String correo, String contra, String compro) {
        if (correo.length()==0) {
            //Toast.makeText(RegistroActivity.this,"Se debe ingresar correo",Toast.LENGTH_LONG).show();
            return false;
        }

        if (contra.equals(compro)) {
            if (contra.length() < 6) {
                //Toast.makeText(RegistroActivity.this,"Se debe ingresar contraseña con al menos 6 caracteres",Toast.LENGTH_LONG).show();
                return false;
            };
            return true;
        }
        ;
        return false;
    }

    //EventoAdapter
    public ArrayList<Evento> mNoticiaData = new ArrayList<Evento>();
    public int getItemCount() {
        return this.mNoticiaData.size();
    }

    //MainActivity
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int caso = 0;
        switch (item.getItemId()) {
            case R.id.nav_boletines:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                //        new BoletinesFragment()).commit();
                caso = 1;
                break;

            case R.id.nav_noticias:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                 //       new NoticiasFragment()).commit();
                caso = 2;
                break;
            case R.id.nav_eventos:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                //        new EventosFragment()).commit();
                caso = 3;
                break;
            case R.id.nav_articulos:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                //        new ArticulosFragment()).commit();
                caso = 4;
                break;
            case R.id.nav_login:
                //Intent intent = new Intent(this, InicioSesionActivity.class);
                //startActivity(intent);
                caso = 5;
            default:
                caso = 6;
                break;

        }
        //drawer.closeDrawer(GravityCompat.START);
        if (caso == 1 ||caso == 2 ||caso == 3 ||caso == 4 ||caso == 5 )
            return true;
        else if(caso == 6){
            return false;
        }
        return false;
    }
}