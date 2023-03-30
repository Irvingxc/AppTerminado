package com.example.plasenciacigar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.plasenciacigar.Interface.ReporteDiarioApi;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;
import com.example.plasenciacigar.models.Diarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Procesar extends Fragment {
    ListView noprocesados;
    Conexion conexion = new Conexion();
    ArrayList<String> fechas = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  procesar= inflater.inflate(R.layout.fragment_procesar, container, false);
        noprocesados = procesar.findViewById(R.id.procesarfechas);
        mostrar();
        noprocesados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String valor = fechas.get(i);
                AlertDialog.Builder confirmar = new AlertDialog.Builder(getContext());
                confirmar.setTitle("Procesar");
                confirmar.setMessage("Estas seguro que deseas procesar la fecha "+valor);
                confirmar.setCancelable(false);
                confirmar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProcesarFecha(valor);
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
            }
        });

        return procesar;
    }

    private void mostrar(){
        Retrofit retrofit = conexion.retrofit();
        ReporteDiarioApi reporteDiarioApi = retrofit.create(ReporteDiarioApi.class);
        reporteDiarioApi.getDiariosNoProcesados().enqueue(new Callback<List<Diarios>>() {
            @Override
            public void onResponse(Call<List<Diarios>> call, Response<List<Diarios>> response) {
                if (response.isSuccessful()) {
                    fechas.clear();
                    List<Diarios> products = response.body();
                    for (Diarios u : products) {
                        fechas.add(u.getFecha());
                    }
                    adapter = new ArrayAdapter<String>(
                            getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, fechas);
                    noprocesados.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "No existen registros para esta fecha", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Diarios>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexion. Contacte al administrador de Sistema(Luis).", Toast.LENGTH_SHORT);
            }
        });
    }

    private void ProcesarFecha(String value){
        Retrofit retrofit = conexion.retrofit();
        ReporteDiarioApi reporteDiarioApi = retrofit.create(ReporteDiarioApi.class);
        reporteDiarioApi.procesados(value).enqueue(new Callback<DetalleProgramacionTerminado>() {
            @Override
            public void onResponse(Call<DetalleProgramacionTerminado> call, Response<DetalleProgramacionTerminado> response) {
                Toast.makeText(getContext(), "Error de conexion. Contacte al administrador de Sistema(Luis).", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<DetalleProgramacionTerminado> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexion. Contacte al administrador de Sistema(Luis).", Toast.LENGTH_SHORT);
            }
        });
    }
}