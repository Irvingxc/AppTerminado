package com.example.plasenciacigar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.plasenciacigar.Adapters.DiariosAdapter;
import com.example.plasenciacigar.Interface.DetalleProgramacionTerminadoApi;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;
import com.example.plasenciacigar.models.DetalleTerminadoDiarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetalleDiarios extends AppCompatActivity {
    String fecha;
    private DiariosAdapter adapter;
    Conexion conexion = new Conexion();
    ListView listView;
    List<DetalleProgramacionTerminado> datos= new ArrayList<DetalleProgramacionTerminado>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_diarios);
        Intent intent =getIntent();
        fecha=intent.getStringExtra("fecha");
        listView = findViewById(R.id.detallediarioslist);
        MostrarFecha(fecha);
    }

    private void MostrarFecha(String fecha){
        Retrofit retrofit = conexion.retrofit();
        DetalleProgramacionTerminadoApi detalleProgramacionTerminadoApi = retrofit.create(DetalleProgramacionTerminadoApi.class);
        detalleProgramacionTerminadoApi.mostrardetallefecha(fecha).enqueue(new Callback<List<DetalleProgramacionTerminado>>() {
            @Override
            public void onResponse(Call<List<DetalleProgramacionTerminado>> call, Response<List<DetalleProgramacionTerminado>> response) {
                if (response.isSuccessful()){
                datos = response.body();
                adapter =new DiariosAdapter(DetalleDiarios.this, datos);
                listView.setAdapter(adapter);
                    Toast.makeText(DetalleDiarios.this, "Excelente", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(DetalleDiarios.this, "Algo salio Mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetalleProgramacionTerminado>> call, Throwable t) {
                Toast.makeText(DetalleDiarios.this, "Fallo de Conexion", Toast.LENGTH_SHORT).show();
            }
        });

    }
}