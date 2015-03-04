package com.siercuit.cartelera.POJOs;

public class PreciosSnacksPOJO
{
    public String imagen_url;
    public String imagen_extension;
    public Snacks[] snacks;

    public static class Snacks
    {
        public String nombre;
        public String precio;
        public String contenido;
        public String imagen;
    }
}
