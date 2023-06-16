package com.example.plasenciacigar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.Toast;

import com.example.plasenciacigar.Interface.ReporteDiarioApi;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;
import com.example.plasenciacigar.models.Diarios;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plasenciacigar.databinding.ActivityMainBinding;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private String fecha;
    private Conexion conexion = new Conexion();
    private AtomicBoolean status = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        fecha = intent.getStringExtra("fecha");
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarProcesos(view);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.existencias
        ,R.id.reporteDiario).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    private void mostrarDatePicker() {
        Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int día = calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int añoSeleccionado, int mesSeleccionado, int díaSeleccionado) {
                String fechaSeleccionada = añoSeleccionado + "-" + (mesSeleccionado+1) + "-" + (díaSeleccionado);
                Diarios diarios = new Diarios();
                diarios.setFecha(fechaSeleccionada);
                conexion.retrofit().create(ReporteDiarioApi.class).registerdiario(diarios).enqueue(new Callback<Diarios>() {
                    @Override
                    public void onResponse(Call<Diarios> call, Response<Diarios> response) {
                        if(response.isSuccessful()) {
                            if (response.body().getFecha() != null) {
                                Intent intent = new Intent(view.getContext(), RegistroDiarios.class);
                                Toast.makeText(MainActivity.this, String.valueOf(response.body().getFecha()), Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Esta fecha ya fue Ingresada", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Diarios> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Algo salio Mal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, año, mes, día);
        datePickerDialog.show();
    }

    private void ValidarProcesos(View view){
        conexion.retrofit().create(ReporteDiarioApi.class).fechaingresar().enqueue(new Callback<Diarios>() {
            @Override
            public void onResponse(Call<Diarios> call, Response<Diarios> response) {
                if (response.isSuccessful()) {
                    if (response.body().getFecha() != null) {
                        Intent intent = new Intent(view.getContext(), RegistroDiarios.class);
                        startActivity(intent);
                    } else {
                        mostrarDatePicker();
                    }
                }
            }
            @Override
            public void onFailure(Call<Diarios> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Algo Salio Mal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                FragmentManager fragmentManager = getSupportFragmentManager();
                NavController c = NavHostFragment.findNavController(fragmentManager.findFragmentById(R.id.nav_host_fragment_content_main));
                c.navigate(R.id.procesar);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}