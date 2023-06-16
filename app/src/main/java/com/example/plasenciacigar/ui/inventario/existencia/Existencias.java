package com.example.plasenciacigar.ui.inventario.existencia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasenciacigar.Adapters.DiariosAdapter;
import com.example.plasenciacigar.Conexion;
import com.example.plasenciacigar.Interface.DetalleProgramacionTerminadoApi;
import com.example.plasenciacigar.R;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Existencias extends Fragment {
    ListView listView;
    TextView amount;
    EditText filtro;
    Conexion conexion = new Conexion();
    private DiariosAdapter diariosAdapter;
    private List<DetalleProgramacionTerminado> datos= new ArrayList<DetalleProgramacionTerminado>();

    public Existencias() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_existencias, container, false);
        listView=view.findViewById(R.id.existenciasempaque);
        amount=view.findViewById(R.id.amountempaque);
        filtro = view.findViewById(R.id.BusquedaMarcas);
        filtro.addTextChangedListener(vFiltro);
        DrawStock(" ");
        DrawAmount();
        return view;
    }

    private TextWatcher vFiltro = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            DrawStock(filtro.getText().toString());
        }
    };

    private void DrawStock(String marca){
        DetalleProgramacionTerminado dpt = new DetalleProgramacionTerminado();
        dpt.setMarca(marca);
        Retrofit retrofit= conexion.retrofit();
        DetalleProgramacionTerminadoApi detalleProgramacionTerminadoApi = retrofit.create(DetalleProgramacionTerminadoApi.class);
        detalleProgramacionTerminadoApi.drawstock(dpt).enqueue(new Callback<List<DetalleProgramacionTerminado>>() {
            @Override
            public void onResponse(Call<List<DetalleProgramacionTerminado>> call, Response<List<DetalleProgramacionTerminado>> response) {
                if (response.isSuccessful()){
                    datos = response.body();
                    diariosAdapter = new DiariosAdapter(getActivity().getApplicationContext(), datos);
                    listView.setAdapter(diariosAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DetalleProgramacionTerminado>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Algo Salio mal, Consulte con el Administrador de Red.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DrawAmount(){
        conexion.retrofit().create(DetalleProgramacionTerminadoApi.class).amountotalempaque()
                .enqueue(new Callback<DetalleProgramacionTerminado>() {
                    @Override
                    public void onResponse(Call<DetalleProgramacionTerminado> call, Response<DetalleProgramacionTerminado> response) {
                        if (response.isSuccessful()){
                            String value = String.valueOf(response.body().getCantidad());
                            amount.setText(value);
                        }
                    }

                    @Override
                    public void onFailure(Call<DetalleProgramacionTerminado> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Algo Salio Mal, Consulte con el administrador de sistema", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}