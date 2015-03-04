package com.siercuit.cartelera.POJOs;

import com.google.gson.annotations.SerializedName;

public class InicioPOJO
{
    public String poster_url;
    public String poster_url_grande;
    public String poster_extension;
    public Carteleras[] carteleras;
    public String mensaje;

    public static class Carteleras
    {
        public String nombre;
        public String icono;
        public Funciones[] funciones;

        public static class Funciones
        {
            public String nombre;
            public String poster;
            public int id;
            public String lenguaje;
            @SerializedName("3D") public Boolean threeD;
        }
    }
}
