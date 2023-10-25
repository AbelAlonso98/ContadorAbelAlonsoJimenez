package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.math.BigInteger;

public class PantallaInicio extends AppCompatActivity {

    float volume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            volume = extras.getFloat("VOLUMEN");
        }
    }
    public void irAMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void irAInfo(View v){
        Intent i = new Intent(this, Info.class);
        startActivity(i);
        finish();
    }

    public void irAOpciones(View v){
        Intent i = new Intent(this, Options.class);
        startActivity(i);
        finish();
    }

    public void closeApp(View view) {
        finishAffinity();
        finish();
        System.exit(0);
    }
}