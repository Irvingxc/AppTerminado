package com.example.plasenciacigar.Interface;

import com.example.plasenciacigar.models.DetalleProgramacionTerminado;
import com.example.plasenciacigar.models.DetalleTerminadoDiarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DetalleProgramacionTerminadoApi {
    @GET("diarios/detalle/{id}")
    Call<DetalleProgramacionTerminado> getProducto(@Path("id") int id);

    @POST("diarios/guardardetalle")
    Call<DetalleTerminadoDiarios> addetalle(@Body DetalleTerminadoDiarios detalleProgramacionTerminado);

    @GET("diarios/mostrardetalle/{fecha}")
    Call<List<DetalleProgramacionTerminado>> mostrardetallefecha(@Path("fecha") String fecha);

    @GET("diarios/delete/{id}")
    Call<DetalleProgramacionTerminado> delete(@Path("id") String id);

    @GET("diarios/existenciasempaque")
    Call<List<DetalleProgramacionTerminado>> drawstock();

    @GET("diarios/mostrardetalle/total/{fecha}")
    Call<DetalleProgramacionTerminado> amountotal(@Path("fecha") String fecha);

    @GET("diarios/existenciasempaque/total")
    Call<DetalleProgramacionTerminado> amountotalempaque();
}
