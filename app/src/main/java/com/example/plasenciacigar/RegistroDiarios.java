package com.example.plasenciacigar;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plasenciacigar.Interface.DetalleProgramacionTerminadoApi;
import com.example.plasenciacigar.databinding.ActivityMainBinding;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;
import com.example.plasenciacigar.models.DetalleTerminadoDiarios;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistroDiarios extends AppCompatActivity {
    Conexion conexion = new Conexion();
    private int iddetalleprogramacionterminado;
    private Button QR;
    EditText id;
    EditText orden;
    EditText numorden;
    EditText item;
    EditText marca;
    EditText vitola;
    EditText capa;
    EditText tipoempaque;

    EditText cantidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_diarios);
        marca = findViewById(R.id.marca);
        orden = findViewById(R.id.orden);
        numorden = findViewById(R.id.numorden);
        item = findViewById(R.id.item);
        vitola = findViewById(R.id.vitola);
        capa = findViewById(R.id.capa);
        tipoempaque = findViewById(R.id.tipoempaque);
        cantidad = findViewById(R.id.cantidadPuros);
        findViewById(R.id.registerAct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetalleTerminadoDiarios detalleTerminadoDiarios = new DetalleTerminadoDiarios();
                detalleTerminadoDiarios.setId_det_progra_term(iddetalleprogramacionterminado);
                detalleTerminadoDiarios.setCantidad(Double.parseDouble(cantidad.getText().toString()));
                Register(detalleTerminadoDiarios);
            }
        });

        Scanner();

    }

    private void Scanner(){
        IntentIntegrator intent = new IntentIntegrator(RegistroDiarios.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intent.setPrompt("Scan");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Cancelaste el proceso", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Consulta(Integer.parseInt(result.getContents()));
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void Consulta(int id){
            Retrofit retrofit = conexion.retrofit();
            DetalleProgramacionTerminadoApi detalleProgramacionTerminadoApi = retrofit.create(DetalleProgramacionTerminadoApi.class);
            detalleProgramacionTerminadoApi.getProducto(id).enqueue(new Callback<DetalleProgramacionTerminado>() {
                @Override
                public void onResponse(Call<DetalleProgramacionTerminado> call, Response<DetalleProgramacionTerminado> response) {
                    if (response.isSuccessful()) {
                        DetalleProgramacionTerminado dd = response.body();
                        iddetalleprogramacionterminado = id;
                        orden.setText(dd.getOrden());
                        marca.setText(dd.getMarca());
                        vitola.setText(dd.getVitola());
                        capa.setText(dd.getCapa());
                        tipoempaque.setText(dd.getTipoempaque());
                        numorden.setText(dd.getNumero_orden());
                        item.setText(dd.getItem());
                    } else {
                        Toast.makeText(RegistroDiarios.this, "Algo Salio Mal, Consulta con el Administrador de Sistema", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DetalleProgramacionTerminado> call, Throwable t) {
                    Toast.makeText(RegistroDiarios.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void Register(DetalleTerminadoDiarios detalleTerminadoDiarios){
        if (detalleTerminadoDiarios.getId_det_progra_term() > 0) {
            Retrofit retrofit = conexion.retrofit();
            DetalleProgramacionTerminadoApi detalleProgramacionTerminadoApi = retrofit.create(DetalleProgramacionTerminadoApi.class);
            detalleProgramacionTerminadoApi.addetalle(detalleTerminadoDiarios).enqueue(
                    new Callback<DetalleTerminadoDiarios>() {
                        @Override
                        public void onResponse(Call<DetalleTerminadoDiarios> call, Response<DetalleTerminadoDiarios> response) {
                            Toast.makeText(RegistroDiarios.this, "Se registro correctamente", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegistroDiarios.this, MainActivity.class);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.putExtra("fecha", LocalDate.now().toString());
                            }
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<DetalleTerminadoDiarios> call, Throwable t) {
                            Toast.makeText(RegistroDiarios.this, "Algo Salio Mal, Consulta con el Administrador de Sistema", Toast.LENGTH_LONG).show();

                        }
                    }
            );
        } else {
            Toast.makeText(RegistroDiarios.this, "Vuelva a Escanear el Codigo", Toast.LENGTH_SHORT).show();
        }
    }
}