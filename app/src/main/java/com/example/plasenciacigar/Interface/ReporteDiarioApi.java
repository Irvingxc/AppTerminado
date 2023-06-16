package com.example.plasenciacigar.Interface;

import com.example.plasenciacigar.RegistroDiarios;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;
import com.example.plasenciacigar.models.DetalleTerminadoDiarios;
import com.example.plasenciacigar.models.Diarios;
import com.example.plasenciacigar.models.DiariosLista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReporteDiarioApi {
    @GET("diarios")
    Call<List<Diarios>> getDiarios();

    @GET("diarios/sinprocesar")
    Call<List<Diarios>> getDiariosNoProcesados();

    @GET("diarios/mostrardetalle/act/{fecha}")
    Call<DetalleProgramacionTerminado> procesados(@Path("fecha") String fecha);

    @GET("diarios/fechaaingresar")
    Call<Diarios> fechaingresar();

    @GET("diarios/editable/{fecha}")
    Call<Diarios> consultarproceso(@Path("fecha") String fecha);

    @POST("diarios/guardaregistrodiario")
    Call<Diarios> registerdiario(@Body Diarios diarios);
}
