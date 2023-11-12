package com.example.contador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemOpcionAdapter extends RecyclerView.Adapter<ItemOpcionAdapter.ViewHolder> {

    List<ItemOpcion> modelList;

    public ItemOpcionAdapter(List<ItemOpcion> userModelList){
        this.modelList = userModelList;
    }

    @NonNull
    @Override
    public ItemOpcionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opcion, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOpcionAdapter.ViewHolder holder, int position) {
        holder.tituloOpcion.setText(modelList.get(position).getTitulo());
        holder.descripcionOpcion.setText(modelList.get(position).getDescripcion());
        holder.imagenOpcion.setImageResource(modelList.get(position).getImagen());
        holder.imagenOpcion.setOnClickListener(modelList.get(position).getAction());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView tituloOpcion;
        private final TextView descripcionOpcion;
        private final ImageView imagenOpcion;

        public ViewHolder(View v){
            super(v);
            tituloOpcion = v.findViewById(R.id.tituloOpcion);
            descripcionOpcion = v.findViewById(R.id.descripcionOpcion);
            imagenOpcion = v.findViewById(R.id.imagenOpcion);

        }
    }
}
