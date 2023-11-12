package com.example.contador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class Info extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        List<ItemColaborador> itemColaboradors = Arrays.asList(
                new ItemColaborador("Abel Alonso Jiménez", "Producción de código", R.drawable.img_abel),
                new ItemColaborador("Nuria Valdés Cuesta", "Producción gráfica", R.drawable.img_nuria),
                new ItemColaborador("Alfonso García Martín", "Direccion ejecutiva", R.drawable.img_fonsi),
                new ItemColaborador("Ignacio Fernández Prieto", "Coproducción de código", R.drawable.img_nacho),
                new ItemColaborador("Pablo Eguilaz Pérez ", "Tester principal", R.drawable.img_pablo),
                new ItemColaborador("Carlos Manso González", "Team leader", R.drawable.img_carlos)
        );
        ItemColaboradorAdapter adapter = new ItemColaboradorAdapter(this, R.layout.item_colaborador, itemColaboradors);
        ListView dataList = findViewById(R.id.dataList);
        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener(this);
    }

    public void irAPantallaInicio(View view) {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}