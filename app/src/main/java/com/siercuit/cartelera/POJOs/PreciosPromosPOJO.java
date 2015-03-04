package com.siercuit.cartelera.POJOs;

public class PreciosPromosPOJO
{
    public Categorias[] categorias;

    public static class Categorias
    {
        public String nombre;
        public Promos[] promos;

        public static class Promos
        {
            public String nombre;
            public String descripcion;
        }
    }
}
