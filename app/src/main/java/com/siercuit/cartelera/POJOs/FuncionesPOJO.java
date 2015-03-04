package com.siercuit.cartelera.POJOs;

import com.google.gson.annotations.SerializedName;

public class FuncionesPOJO
{
    public String poster_url;
    public String poster_url_grande;
    public String poster_extension;
    public Funciones[] funciones;
    public String mensaje;

    public static class Funciones
    {
        public int id;
        public String nombre;
        public String lenguaje;
        @SerializedName("3D") public Boolean threeD;
        public String estreno;
        public int rating;
        public String poster;
    }
}
