package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView contador;
    ImageView moneda;

    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


    BigInteger num = new BigInteger("500");
    BigDecimal numD = new BigDecimal("0");
    BigInteger inc = new BigInteger("1");
    BigInteger incAuto = new BigInteger("1");

    int tiempoAutoClick = 1000;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            num = new BigInteger(extras.getString("MONEY_COUNT"));
            inc = new BigInteger(extras.getString("CLICK_VALUE"));
            incAuto = new BigInteger(extras.getString("AUTOCLICK_VALUE"));
            tiempoAutoClick = extras.getInt("AUTOCLICK_TIME");
        }

        //  Cargo todos los componentes que voy a usar.
        contador = (TextView) findViewById(R.id.textocontador);
        moneda = (ImageView) findViewById(R.id.moneda);

        textValorClick = (TextView) findViewById(R.id.textValorClick);
        textValorAutoClick = (TextView) findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = (TextView) findViewById(R.id.textVelocidadAutoClick);

        // Cargo animaciones
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
        if (num.longValue() >= 1000000000) {
            numD = BigDecimal.valueOf(num.longValue() / 1000000000d);
            contador.setText(df.format(numD) + "B");
        } else if (num.longValue() >= 1000000) {
            numD = BigDecimal.valueOf(num.longValue() / 1000000d);
            contador.setText(df.format(numD) + "M");
        } else if (num.longValue() >= 1000) {
            numD = BigDecimal.valueOf(num.longValue() / 1000d);
            contador.setText(df.format(numD) + "K");
        } else
            contador.setText(num.toString());

        textValorClick.setText("Click: " + inc.toString());
        textValorAutoClick.setText("Autoclick: " + incAuto.toString());
        textVelocidadAutoClick.setText("Autoclick speed: " + tiempoAutoClick + "m" + "s");
    }

    public void reset(View v) {
        num = BigInteger.valueOf(0);
        setContText();
    }

    public void irAPantallaInicio(View view) {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
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
        mediaPlayer.release();
        super.onStop();
    }
}