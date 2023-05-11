package com.example.plasenciacigar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.plasenciacigar.Adapters.DiariosAdapter;
import com.example.plasenciacigar.Interface.DetalleProgramacionTerminadoApi;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;
import com.example.plasenciacigar.models.DetalleTerminadoDiarios;
import com.example.plasenciacigar.models.Diarios;

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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetalleProgramacionTerminado valor = adapter.getItem(i);
                AlertDialog.Builder confirmar = new AlertDialog.Builder(getApplicationContext());
                confirmar.setTitle("Procesar");
                confirmar.setMessage("Estas seguro que deseas eliminar este producto "+valor);
                confirmar.setCancelable(false);
                confirmar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete(valor);
                        adapter.remove(valor);
                        adapter.notifyDataSetChanged();
                    }
                });


                confirmar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                confirmar.show();
                return false;
            }
        });
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

   /* private void delete(DetalleProgramacionTerminado dd){
        Retrofit retrofit = conexion.retrofit();
        DetalleProgramacionTerminadoApi detalleProgramacionTerminadoApi = retrofit.create(DetalleProgramacionTerminadoApi.class);
        detalleProgramacionTerminadoApi.delete(fecha).enqueue(new Callback<DetalleProgramacionTerminado>() {
            @Override
            public void onResponse(Call<DetalleProgramacionTerminado> call, Response<DetalleProgramacionTerminado> response) {
                response.body();
                adapter.remove(dd);
                adapter.notifyDataSetChanged();
                Toast.makeText(DetalleDiarios.this, "Producto eliminado correctamente.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DetalleProgramacionTerminado> call, Throwable t) {

            }
        });

    }
    */
}