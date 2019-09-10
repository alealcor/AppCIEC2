package com.example.jonathan.appciec;

import com.example.jonathan.appciec.Models.SessionHandler;
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SesionHandlerTest {
    private final SPMockBuilder spMockBuilder = new SPMockBuilder();
    private final SessionHandler handlerTest= new SessionHandler(spMockBuilder.createContext());

    @Test
    public void sesionGuardadaExitosaTest() {
        assertTrue(handlerTest.guardarSesion("prueba@hotmail.com", "prueba12345"));
    }

    @Test
    public void sesionNoGuardadaPorFaltaDeCorreoTest() {
        assertFalse(handlerTest.guardarSesion("", "prueba12345"));
    }

    @Test
    public void sesionNoGuardadaPorFaltaDeTokenTest() {
        assertFalse(handlerTest.guardarSesion("prueba@hotmail.com", ""));
    }

    @Test
    public void sesionIniciadaTest() {
        handlerTest.guardarSesion("prueba@hotmail.com", "prueba12345");
        assertTrue(handlerTest.estaIniciado());
    }

    @Test
    public void sesionCerradaTest() {
        handlerTest.guardarSesion("prueba@hotmail.com", "prueba12345");
        handlerTest.cerrarSesion();
        assertFalse(handlerTest.estaIniciado());
    }
    @Test
    public void verificarTokenTest() {
        handlerTest.guardarSesion("prueba@hotmail.com", "prueba12345");
        assertEquals("prueba12345",handlerTest.usuarioId());
    }

    @Test
    public void verificarCorreoTest() {
        handlerTest.guardarSesion("prueba@hotmail.com", "prueba12345");
        assertEquals("prueba@hotmail.com",handlerTest.usuarioCorreo());
    }
    @Test
    public void sesionNoIniciadaTest() {
        assertFalse(handlerTest.estaIniciado());
    }
}