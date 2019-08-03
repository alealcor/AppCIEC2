package com.example.jonathan.appciec;

        import android.content.*;
        import android.content.pm.ApplicationInfo;
        import android.content.pm.PackageManager;
        import android.content.res.AssetManager;
        import android.content.res.Configuration;
        import android.content.res.Resources;
        import android.database.DatabaseErrorHandler;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Bitmap;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Looper;
        import android.os.UserHandle;
        import android.view.Display;
        import org.junit.Test;

        import java.io.*;
        import java.util.ArrayList;

        import static org.junit.Assert.*;

public class MetodosdePruebaTest {
    MetodosdePrueba prueba = new MetodosdePrueba();
    @Test
    public void validarUsuarioValido() {

        assertEquals(true, prueba.validarUsuarioRegistro("jfparral@espol.edu.ec","123456","123456"));
    }

    @Test
    public void validarUsuarioInValidoContra() {

        assertEquals(false, prueba.validarUsuarioRegistro("jfparral@espol.edu.ec","123456","125456"));
    }

    @Test
    public void validarUsuarioLogin() {
        assertEquals(true, prueba.validarUsuarioLogin("jfparral@gmail.com","123456"));
    }

    @Test
    public void validarUsuarioLoginNoValido() {
        assertEquals(false, prueba.validarUsuarioLogin("jfparral@espol.edu.ec","123456"));
    }

    @Test
    public void validarUsuarioLoginNoValido2() {
        assertEquals(false, prueba.validarUsuarioLogin("jfparral@espol@edu.ec","123456"));
    }

    @Test
    public void validarUsuarioLoginNoValido3() {
        assertEquals(false, prueba.validarUsuarioLogin("jfparral@espol@edu.ec",""));
    }

    @Test
    public void validarUsuarioLoginNoValido4() {
        assertEquals(false, prueba.validarUsuarioLogin("jfparral@espol@edu.ec","1234"));
    }

    @Test
    public void getItemCount() {
        MetodosdePrueba event = new MetodosdePrueba();
        event.mNoticiaData.add(new Evento(1));
        assertEquals(1, event.getItemCount());
    }

    @Test
    public void getItemCountNoValido() {
        MetodosdePrueba event = new MetodosdePrueba();
        event.mNoticiaData.add(new Evento(1));
        event.mNoticiaData.add(new Evento(2));
        assertEquals(2, event.getItemCount());
    }

    @Test
    public void onNavigationItemSelected() {
    }
}