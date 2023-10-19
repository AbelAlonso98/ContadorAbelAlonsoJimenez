package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
    }
    public void irAMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void irAInfo(View v){
        Intent i = new Intent(this, Info.class);
        startActivity(i);
    }

    public void irAOpciones(View v){
        Intent i = new Intent(this, Options.class);
        startActivity(i);
    }

    public void closeApp(View view) {
        finishAffinity();
        finish();
        System.exit(0);
    }
}