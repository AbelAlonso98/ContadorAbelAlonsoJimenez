package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    Button botonSumaTotal;
    Button botonSumarAutoTotal;
    Button botonSumarAutoSpeedTotal;
    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


    BigInteger num = new BigInteger("0");
    BigDecimal numD = new BigDecimal("0");
    BigInteger inc = new BigInteger("1");
    BigInteger incAuto = new BigInteger("1");

    int tiempoAutoClick = 1000;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Cargo todos los componentes que voy a usar.
        contador = (TextView) findViewById(R.id.textocontador);
        moneda = (ImageView) findViewById(R.id.moneda);
        botonSumaTotal = (Button) findViewById(R.id.botonSumarTotal);
        botonSumarAutoTotal = (Button) findViewById(R.id.botonSumarAutoTotal);
        botonSumarAutoSpeedTotal = (Button) findViewById(R.id.botonSumarAutoSpeedTotal);
        textValorClick = (TextView) findViewById(R.id.textValorClick);
        textValorAutoClick = (TextView) findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = (TextView) findViewById(R.id.textVelocidadAutoClick);

        // Cargo animaciones
        fade_in.setDuration(100);

        // Cargo el texto con la funcion que lo formatea e inicio el sumar auto.
        setContText();
        sumarAuto();
    }

    public void sumar(View v) {
        num = num.add(inc);
        setContText();
        moneda.startAnimation(fade_in);
    }

    public void sumarAuto(){
        new Thread(() -> {
            while (true){
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

    public void setContText(){


        if(num.longValue()>=1000000000){
            numD = BigDecimal.valueOf(num.longValue()/1000000000d);
            contador.setText(df.format(numD) + "B");
        }
        else if(num.longValue()>=1000000){
            numD = BigDecimal.valueOf(num.longValue()/1000000d);
            contador.setText(df.format(numD) +"M");
        }

        else if(num.longValue()>=1000){
            numD = BigDecimal.valueOf(num.longValue()/1000d);
            contador.setText(df.format(numD) +"K");
        }
        else
            contador.setText(num.toString());
        botonSumaTotal.setText("100$ x" + num.divide(BigInteger.valueOf(100)));
        botonSumarAutoTotal.setText("200$ x" + num.divide(BigInteger.valueOf(200)));
        botonSumarAutoSpeedTotal.setText("400$ x" + num.divide(BigInteger.valueOf(400)));
        textValorClick.setText("Click: " + inc.toString());
        textValorAutoClick.setText("Autoclick: "+ incAuto.toString());
        textVelocidadAutoClick.setText("Autoclick speed: " + tiempoAutoClick + "m" +
                "s");
    }

    public void sumaTotal(View v){
        // inc += num/100 and num=num%100
        inc = inc.add(num.divide(BigInteger.valueOf(100)));
        num = num.remainder(BigInteger.valueOf(100));
        contador.setText(num.toString());
        botonSumaTotal.setText("100$ x" + num.divide(BigInteger.valueOf(100)).toString());
    }

    public void sumaTotalAuto(View v){
        incAuto = incAuto.add(num.divide(BigInteger.valueOf(200)));
        num = num.remainder(BigInteger.valueOf(200));
        contador.setText(num.toString());
        botonSumarAutoTotal.setText("200$ x" + num.divide(BigInteger.valueOf(200)).toString());
    }

    public void incrementarAutoSpeed(View v){
        if(num.longValue() >= 400) {
            tiempoAutoClick = (int) (tiempoAutoClick / 1.5);
            num = num.subtract(BigInteger.valueOf(400));
        }
    }

    public void incrementarAutoSpeedTotal(View v){
        if(num.longValue() >= 400){
            int times = num.divide(BigInteger.valueOf(400)).intValue();
            tiempoAutoClick = (int) (tiempoAutoClick/(1.5*times));
        num = num.remainder(BigInteger.valueOf(400));
        contador.setText(num.toString());
        botonSumarAutoSpeedTotal.setText("400$ x" + times);
        }
    }

    public void incrementar(View v){
        if(num.longValue() >= 100) {
            inc = inc.add(BigInteger.valueOf(1));
            num = num.subtract(BigInteger.valueOf(100));
            contador.setText(num.toString());
        }
    }

    public void incrementarAuto(View v){
        if(num.longValue() >= 200){
            incAuto = incAuto.add(BigInteger.valueOf(1));
            num = num.subtract(BigInteger.valueOf(200));
            contador.setText(num.toString());
        }
    }

    public void reset(View v){
        num = BigInteger.valueOf(0);
        contador.setText(num.toString());
    }

    public void irAPantallaInicio(View view) {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);

    }
}