package com.example.plasenciacigar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conexion {

    public Retrofit retrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.2.141:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
