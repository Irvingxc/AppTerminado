package com.example.plasenciacigar;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.plasenciacigar.Interface.ReporteDiarioApi;
import com.example.plasenciacigar.models.Diarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Tag;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReporteDiario#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ReporteDiario extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ListView listView;
    String[] elementos;
    DetalleDiariosMostrar detalleDiariosMostrar = new DetalleDiariosMostrar();
    Conexion conexion = new Conexion();

    List<Diarios> list = new ArrayList<Diarios>();
    ArrayList<Diarios> diarios = new ArrayList<>();
    ArrayList<String> fechas = new ArrayList<String>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReporteDiario.
     */
    // TODO: Rename and change types and number of parameters
    public static ReporteDiario newInstance(String param1, String param2) {
        ReporteDiario fragment = new ReporteDiario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ReporteDiario() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reporte_diario, container, false);
        listView = view.findViewById(R.id.listaactividades);
        Consulta();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetalleDiariosMostrar detalleDiariosMostrar = new DetalleDiariosMostrar();
                Bundle result = new Bundle();
                result.putString("fecha", fechas.get(i));
                NavController c = NavHostFragment.findNavController(ReporteDiario.this);
                c.navigate(R.id.todetalle, result);
            }
        });
        return view;
    }

    private void Consulta(){
        Retrofit retrofit = conexion.retrofit();
        ReporteDiarioApi reporteDiarioApi = retrofit.create(ReporteDiarioApi.class);
        reporteDiarioApi.getDiarios().enqueue(new Callback<List<Diarios>>() {
            @Override
            public void onResponse(Call<List<Diarios>> call, Response<List<Diarios>> response) {
                if (response.isSuccessful()) {
                    fechas.clear();
                    List<Diarios> products = response.body();
                    for (Diarios u : products) {
                        fechas.add(u.getFecha());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, fechas);
                    listView.setAdapter(adapter);
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

}