package com.example.contador;

import android.view.View;

public class ItemOpcion {
    private final String titulo;
    private final String descripcion;
    private final int imagen;


    private final View.OnClickListener action;

    public ItemOpcion(String titulo, String descripcion, int imagen, View.OnClickListener action) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.action = action;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public View.OnClickListener getAction() {
        return action;
    }
}
