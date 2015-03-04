package com.siercuit.cartelera.POJOs;

public class PreciosEntradasPOJO
{
    public Precios[] precios;

    public static class Precios
    {
        public String nombre;
        public Opciones[] opciones;

        public static class Opciones
        {
            public String nombre;
            public Horarios[] horarios;

            public static class Horarios
            {
                public String nombre;
                public String nombre_sub;
                public String precio;
            }
        }
    }
}
