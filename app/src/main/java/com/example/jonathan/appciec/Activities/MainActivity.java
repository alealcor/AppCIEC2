package com.example.jonathan.appciec.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.example.jonathan.appciec.Fragments.*;
import com.example.jonathan.appciec.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle  toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new MenuFragment()).commit();
        cargarSesion();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_boletines:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                        new BoletinesFragment()).commit();
                break;

            case R.id.nav_noticias:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                        new NoticiasFragment()).commit();
                break;
            case R.id.nav_eventos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                        new EventosFragment()).commit();
                break;
            case R.id.nav_articulos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                        new ArticulosFragment()).commit();
                break;
            case R.id.nav_login:
                Intent intent = new Intent(this, InicioSesionActivity.class);
                startActivity(intent);
                break;
            case R.id.cerrar_sesion:
                cerrarSesion();
            default:
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                    new MenuFragment()).commit();
        }
    }

    public void verBoletines(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new BoletinesFragment()).commit();
    }

    public void verEventos(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new EventosFragment()).commit();
    }

    public void verNoticias(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new NoticiasFragment()).commit();
    }

    public void verArticulos(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new ArticulosFragment()).commit();

    }

    public void cargarSesion(){
        SharedPreferences preferencias = getSharedPreferences("Credenciales.sesion",Context.MODE_PRIVATE);

        if (preferencias.getBoolean("log",false)){
            String crr = preferencias.getString("correo","No esta registrado");
            String token = preferencias.getString("token","No esta registrado");
        }
        else{
            Log.e("sesion","no hay sesion iniciada");
        }
    }

    public void cerrarSesion(){
        SharedPreferences preferences = getSharedPreferences("Credenciales.sesion", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("log",false).apply();
        preferences.edit().putString("correo",null).apply();
        preferences.edit().putString("token",null).apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public boolean estaIniciado(){
        SharedPreferences preferencias = getSharedPreferences("Credenciales.sesion",Context.MODE_PRIVATE);
        return preferencias.getBoolean("log",false);
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
