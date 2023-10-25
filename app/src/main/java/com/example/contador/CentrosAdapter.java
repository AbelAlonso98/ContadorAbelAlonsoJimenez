package com.example.contador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CentrosAdapter extends ArrayAdapter<Centro> {

    public CentrosAdapter(@NonNull Context context, int resource, @NonNull List<Centro> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Centro c = getItem(position);
        if(convertView  == null)
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.item, parent, false);
        ((ImageView) convertView.findViewById(R.id.imagenDeMierda)).setImageResource(c.getImagen());
        ((TextView) convertView.findViewById(R.id.textoDeMierda)).setText(c.getNombre());
        ((TextView) convertView.findViewById(R.id.textoDeMierda2)).setText(c.getDireccion());
        return convertView;
    }
}
