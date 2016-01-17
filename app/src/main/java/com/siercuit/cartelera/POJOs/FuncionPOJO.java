package com.siercuit.cartelera.POJOs;

import com.google.gson.annotations.SerializedName;

public class FuncionPOJO
{
    public String poster_url;
    public String poster_url_grande;
    public String poster_extension;
    public Funcion funcion;

    public static class Funcion
    {
        public String nombre;
        public String lenguaje;
        @SerializedName("3D") public Boolean threeD;
        public String poster;
        public int rating;
        public String estreno;
        public String clasificacion;
        public String genero;
        public String duracion;
        public String descripcion;
        public Salas[] salas;

        public static class Salas
        {
            public String id;
            public String nombre;
            public Horarios[] horarios;

            public static class Horarios
            {
                public String dia;
                public String[] horas;
            }
        }
    }
}
