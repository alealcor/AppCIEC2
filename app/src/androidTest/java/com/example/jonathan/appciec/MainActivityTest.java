package com.example.jonathan.appciec;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void verBoletines() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.jonathan.appciec", appContext.getPackageName());
    }

    @Test
    public void verEventos() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.jonathan.appciec", appContext.getPackageName());
    }

    @Test
    public void verNoticias() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.jonathan.appciec", appContext.getPackageName());
    }

    @Test
    public void verInvestigaciones() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.jonathan.appciec", appContext.getPackageName());
    }
}