package com.example.jonathan.appciec;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class busquedaPorTitulo {
    @Test
    public void busquedaNula() {
        String query = null;
        assertFalse(PaperAdapter.validateTitleSearchQuery(query));

    }

    @Test
    public void busquedaVacia() {
        String query = "";
        assertTrue(PaperAdapter.validateTitleSearchQuery(query));

    }

    @Test
    public void busquedaSinCaracteresEspeciales() {
        String query = "diseño";
        assertTrue(PaperAdapter.validateTitleSearchQuery(query));

    }

    @Test
    public void busquedaCaracteresEspeciales() {
        String query = "diseño, produccion y venta??";
        assertFalse(PaperAdapter.validateTitleSearchQuery(query));

    }
    @Test
    public void busquedaNumeros() {
        String query = "2018";
        assertTrue(PaperAdapter.validateTitleSearchQuery(query));

    }
    @Test
    public void busquedaTildes() {
        String query = "comunicación";
        assertTrue(PaperAdapter.validateTitleSearchQuery(query));

    }

    @Test
    public void busquedaQueryLargo() {
        String query = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, " +
                "but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages," +
                " and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        assertFalse(PaperAdapter.validateTitleSearchQuery(query));

    }

}