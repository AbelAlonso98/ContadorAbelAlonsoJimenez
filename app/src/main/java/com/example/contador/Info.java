package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.Arrays;
import java.util.List;

public class Info extends ListActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        List<Centro> centros = Arrays.asList(
                new Centro("Centro 1", "Direccion 1", R.drawable.back_orange),
                new Centro("Centro 2", "Direccion 2", R.drawable.back_orange),
                new Centro("Centro 3", "Direccion 3", R.drawable.back_orange)
        );
        CentrosAdapter adapter = new CentrosAdapter(this, R.layout.item, centros);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void irAPantallaInicio(View view) {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}