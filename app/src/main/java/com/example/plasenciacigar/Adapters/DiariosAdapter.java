package com.example.plasenciacigar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.example.plasenciacigar.R;
import com.example.plasenciacigar.models.DetalleProgramacionTerminado;
import com.example.plasenciacigar.models.Diarios;

import java.util.List;

public class DiariosAdapter extends ArrayAdapter<DetalleProgramacionTerminado> {
    private Context context;


    public DiariosAdapter(@NonNull Context context, List<DetalleProgramacionTerminado>datos) {
        super(context, R.layout.item_detalle,datos);
        this.context =context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detalle, null);

        DetalleProgramacionTerminado d =getItem(position);
        TextView marca = convertView.findViewById(R.id.marca_item);
        TextView vitola = convertView.findViewById(R.id.vitola_item);
        TextView item = convertView.findViewById(R.id.item_item);
        TextView cantidad = convertView.findViewById(R.id.cantidad_item);

        marca.setText(d.getMarca().concat("   ").concat(d.getNombre()));
        vitola.setText(d.getVitola().concat("   ").concat(d.getCapa().concat("   ").concat(d.getTipoempaque())));
        item.setText(d.getItem().concat("   ").concat(d.getOrden()).concat("   ").concat(d.getNumero_orden()));
        if (d.getCantidadbultos()!=null && d.getCantidad()!=null && d.getUnidades()!=null) {
            cantidad.setText(d.getCantidadbultos().concat("   ").concat(d.getUnidades().concat("   ").concat(d.getCantidad())));
        }else {
            cantidad.setText(d.getCantidad());
        }
        return convertView;
    }
}
