package com.example.plasenciacigar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.plasenciacigar.Adapters.DiariosAdapter;
import com.example.plasenciacigar.Interface.DetalleProgramacionTerminadoApi;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetalleDiariosMostrar extends Fragment {
    private String result;
    private String fecha;
    private DiariosAdapter adapter;
    Conexion conexion = new Conexion();
    ListView listView;
    List<DetalleProgramacionTerminado> datos= new ArrayList<DetalleProgramacionTerminado>();

    // TODO: Rename and change types of parameters
    private String getFecha;

    public DetalleDiariosMostrar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fecha = LocalDate.now().toString();
        }

        if (getArguments() != null) {
            getFecha = getArguments().getString("fecha");
        } else {
            getFecha = fecha;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detalle_diarios_mostrar, container, false);
        listView = view.findViewById(R.id.detallediarioslist);
        MostrarFecha(getFecha);
        return view;
    }

    private void MostrarFecha(String fecha){
        Retrofit retrofit = conexion.retrofit();
        DetalleProgramacionTerminadoApi detalleProgramacionTerminadoApi = retrofit.create(DetalleProgramacionTerminadoApi.class);
        detalleProgramacionTerminadoApi.mostrardetallefecha(fecha).enqueue(new Callback<List<DetalleProgramacionTerminado>>() {
            @Override
            public void onResponse(Call<List<DetalleProgramacionTerminado>> call, Response<List<DetalleProgramacionTerminado>> response) {
                if (response.isSuccessful()){
                    datos = response.body();
                    adapter =new DiariosAdapter(getActivity().getApplicationContext(), datos);
                    listView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "Algo salio Mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetalleProgramacionTerminado>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Fallo de Conexion", Toast.LENGTH_SHORT).show();
            }
        });

    }
}