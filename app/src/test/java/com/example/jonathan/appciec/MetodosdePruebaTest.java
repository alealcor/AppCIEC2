package com.example.jonathan.appciec;

        import com.example.jonathan.appciec.Activities.MetodosdePrueba;
        import com.example.jonathan.appciec.Models.Evento;
        import org.junit.Test;

        import static org.junit.Assert.*;

public class MetodosdePruebaTest {
    private final MetodosdePrueba prueba = new MetodosdePrueba();
    @Test
    public void validarUsuarioValido() {

        assertTrue(prueba.validarUsuarioRegistro("jfparral@espol.edu.ec", "123456", "123456"));
    }

    @Test
    public void validarUsuarioInValidoContra() {

        assertFalse(prueba.validarUsuarioRegistro("jfparral@espol.edu.ec", "123456", "125456"));
    }

    @Test
    public void validarUsuarioLogin() {
        assertTrue(prueba.validarUsuarioLogin("jfparral@gmail.com", "123456"));
    }

    @Test
    public void validarUsuarioLoginNoValido() {
        assertFalse(prueba.validarUsuarioLogin("jfparral@espol.edu.ec", "123456"));
    }

    @Test
    public void validarUsuarioLoginNoValido2() {
        assertFalse(prueba.validarUsuarioLogin("jfparral@espol@edu.ec", "123456"));
    }

    @Test
    public void validarUsuarioLoginNoValido3() {
        assertFalse(prueba.validarUsuarioLogin("jfparral@espol@edu.ec", ""));
    }

    @Test
    public void validarUsuarioLoginNoValido4() {
        assertFalse(prueba.validarUsuarioLogin("jfparral@espol@edu.ec", "1234"));
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