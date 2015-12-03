package com.siercuit.cartelera.POJOs;

import com.google.gson.annotations.SerializedName;

public class HomePOJO
{
    public String message;
    public PosterPOJO poster;
    public Categories[] categories;

    public static class Categories
    {
        public String name;
        public String icon;
        public Items[] items;

        public static class Items
        {
            public String name;
            public String poster;
            public int id;
            public String language;
            @SerializedName("3D") public Boolean threeD;
        }
    }
}
