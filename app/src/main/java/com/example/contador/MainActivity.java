package com.example.contador;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    TextView contador;
    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    BigDecimal num = new BigDecimal("567");
    BigDecimal inc = new BigDecimal("1");
    BigDecimal incAuto = new BigDecimal("1");

    int tiempoAutoClick = 1000;

    MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            num = new BigDecimal(extras.getString("MONEY_COUNT", "0"));
            inc = new BigDecimal(extras.getString("CLICK_VALUE", "0"));
            incAuto = new BigDecimal(extras.getString("AUTOCLICK_VALUE", "0"));
            tiempoAutoClick = extras.getInt("AUTOCLICK_TIME");
        }

        //  Cargo todos los componentes que voy a usar.
        contador = findViewById(R.id.textocontador);
        textValorClick = findViewById(R.id.textValorClick);
        textValorAutoClick = findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = findViewById(R.id.textVelocidadAutoClick);

        // Cargo animaciones y musica
        fade_in.setDuration(100);
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // Cargo el texto con la funcion que lo formatea e inicio el sumar auto.
        setContText();
        sumarAuto();
    }

    public void sumar(View v) {
        num = num.add(inc);
        setContText();
    }

    public void sumarAuto() {
        new Thread(() -> {
            while (true) {
                num = num.add(incAuto);
                runOnUiThread(this::setContText);
                try {
                    Thread.sleep(tiempoAutoClick);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }


    public void setContText() {

        // Esta opcion con un mapa, la veo más bonita y estable.
        HashMap<String, BigDecimal> VALORES = new LinkedHashMap<>();

        VALORES.put("K", new BigDecimal("1000"));
        VALORES.put("M", new BigDecimal("1000000"));
        VALORES.put("B", new BigDecimal("1000000000"));
        VALORES.put("T", new BigDecimal("1000000000000"));
        VALORES.put("C", new BigDecimal("1000000000000000"));
        VALORES.put("Q", new BigDecimal("1000000000000000000"));
        VALORES.put("S", new BigDecimal("1000000000000000000000"));
        VALORES.put("H", new BigDecimal("10000000000000000000000000"));
        VALORES.put("O", new BigDecimal("10000000000000000000000000000"));
        VALORES.put("N", new BigDecimal("10000000000000000000000000000000"));
        VALORES.put("D", new BigDecimal("10000000000000000000000000000000000"));
        VALORES.put("UD", new BigDecimal("10000000000000000000000000000000000000"));
        VALORES.put("DD", new BigDecimal("1000000000000000000000000000000000000000"));
        VALORES.put("TD", new BigDecimal("1000000000000000000000000000000000000000000"));
        VALORES.put("CD", new BigDecimal("1000000000000000000000000000000000000000000000"));
        VALORES.put("QD", new BigDecimal("1000000000000000000000000000000000000000000000000"));
        VALORES.put("SD", new BigDecimal("1000000000000000000000000000000000000000000000000000"));
        VALORES.put("HD", new BigDecimal("1000000000000000000000000000000000000000000000000000000"));
        VALORES.put("OD", new BigDecimal("1000000000000000000000000000000000000000000000000000000000"));
        VALORES.put("ND", new BigDecimal("1000000000000000000000000000000000000000000000000000000000000"));
        VALORES.put("V", new BigDecimal("10000000000000000000000000000000000000000000000000000000000000000"));

        if (num.compareTo(VALORES.get("K")) < 0)
            contador.setText(num.toString());
        else {
            for (String s : VALORES.keySet()) {
                if (num.compareTo(VALORES.get(s)) >= 0)
                    contador.setText(num.divide(VALORES.get(s)).setScale(2, RoundingMode.HALF_EVEN).toString() + s);
            }
        }

        // Esta opcion con dos arrays, el inconveniente es que ambos han de tener las mismas dimensiones.
//        for(String s: VALORES.keySet()){
//            if(num.compareTo(VALORES.get(s)) >= 0)
//                contador.setText(num.divide(VALORES.get(s)).setScale(2, RoundingMode.HALF_EVEN).toString() + s);
//
//        }
//        String[] SIGLAS = {"K", "M", "G", "T", "P", "E", "Z", "Y", "R", "Q", "D"};
//        BigDecimal[] NUMBERS = {
//                new BigDecimal("1000000"),
//                new BigDecimal("1000000000"),
//                new BigDecimal("1000000000000"),
//                new BigDecimal("1000000000000000"),
//                new BigDecimal("1000000000000000000"),
//                new BigDecimal("1000000000000000000000"),
//                new BigDecimal("1000000000000000000000000"),
//                new BigDecimal("1000000000000000000000000000"),
//                new BigDecimal("1000000000000000000000000000000"),
//                new BigDecimal("1000000000000000000000000000000000")};
//        for (int i = 0; i < NUMBERS.length; i++) {
//            BigDecimal n = NUMBERS[i];
//            if (num.compareTo(n) >= 0)
//                contador.setText(num.divide(n).setScale(2, RoundingMode.HALF_EVEN).toString() + SIGLAS[i]);
//        }
        textValorClick.setText("Click: " + inc.toString());
        textValorAutoClick.setText("Autoclick: " + incAuto.toString());
        textVelocidadAutoClick.setText("Autoclick speed: " + tiempoAutoClick + "m" + "s");
    }

    public void reset(View v) {

        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setCancelable(true);
        constructor.setTitle("Reset");
        constructor.setMessage("¿Seguro que quieres resetear el progreso?");
        constructor.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                num = new BigDecimal("0");
                setContText();
            }
        });
        constructor.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = constructor.create();
        dialog.show();

    }

    public void irAPantallaInicio(View view) {

        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setCancelable(true);
        constructor.setTitle("Salir");
        constructor.setMessage("Si sales perderás el progreso, ¿quieres salir?");
        constructor.setPositiveButton("SALIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                salir();
            }
        });
        constructor.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = constructor.create();
        dialog.show();

    }

    public void salir(){
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
        finish();
    }

    public void irACompras(View view) {
        Intent i = new Intent(this, Compras.class);
        i.putExtra("MONEY_COUNT", num.toString());
        i.putExtra("CLICK_VALUE", inc.toString());
        i.putExtra("AUTOCLICK_VALUE", incAuto.toString());
        i.putExtra("AUTOCLICK_TIME", tiempoAutoClick);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}