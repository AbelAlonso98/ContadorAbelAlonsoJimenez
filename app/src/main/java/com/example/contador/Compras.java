package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Compras extends AppCompatActivity {

    BigDecimal num;
    BigDecimal inc;
    BigDecimal incAuto;
    int tiempoAutoClick;

    Button botonSumaTotal;
    Button botonSumarAutoTotal;
    Button botonSumarAutoSpeedTotal;

    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    TextView textMoneyCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            num = new BigDecimal(extras.getString("MONEY_COUNT"));
            inc = new BigDecimal(extras.getString("CLICK_VALUE"));
            incAuto = new BigDecimal(extras.getString("AUTOCLICK_VALUE"));
            tiempoAutoClick = extras.getInt("AUTOCLICK_TIME");
        }


        botonSumaTotal = (Button) findViewById(R.id.botonSumarTotal);
        botonSumarAutoTotal = (Button) findViewById(R.id.botonSumarAutoTotal);
        botonSumarAutoSpeedTotal = (Button) findViewById(R.id.botonSumarAutoSpeedTotal);
        textValorClick = (TextView) findViewById(R.id.textValorClick);
        textValorAutoClick = (TextView) findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = (TextView) findViewById(R.id.textVelocidadAutoClick);
        textMoneyCount = (TextView) findViewById(R.id.txtMoneyCount);
        setContText();
    }

    public void volverAJugar(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MONEY_COUNT", num.toString());
        i.putExtra("CLICK_VALUE", inc.toString());
        i.putExtra("AUTOCLICK_VALUE", incAuto.toString());
        i.putExtra("AUTOCLICK_TIME", tiempoAutoClick);
        startActivity(i);
        finish();
    }

    public void setContText() {
        // El texto de los botones de la derecha es dinamico, se cargan aquí
        botonSumaTotal.setText("100$ x" + num.divide(BigDecimal.valueOf(100), BigDecimal.ROUND_DOWN));
        botonSumarAutoTotal.setText("200$ x" + num.divide(BigDecimal.valueOf(200), BigDecimal.ROUND_DOWN));
        botonSumarAutoSpeedTotal.setText("400$ x" + num.divide(BigDecimal.valueOf(400), BigDecimal.ROUND_DOWN));

        // Cargo los datos de la barra superior
        textValorClick.setText("Click: " + inc.toString());
        textValorAutoClick.setText("Autoclick: " + incAuto.toString());
        textVelocidadAutoClick.setText("Autoclick speed: " + tiempoAutoClick + "m" + "s");

        // Formateo el contador.
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
            textMoneyCount.setText(num.toString());
        else {
            for (String s : VALORES.keySet()) {
                if (num.compareTo(VALORES.get(s)) >= 0)
                    textMoneyCount.setText(num.divide(VALORES.get(s)).setScale(2, RoundingMode.HALF_EVEN).toString() + s);
            }
        }
    }

    public void sumaTotal(View v) {
        // inc += num/100 and num=num%100
        inc = inc.add(num.divide(BigDecimal.valueOf(100), BigDecimal.ROUND_DOWN));
        num = num.remainder(BigDecimal.valueOf(100));
        setContText();
    }

    public void sumaTotalAuto(View v) {
        incAuto = incAuto.add(num.divide(BigDecimal.valueOf(200), BigDecimal.ROUND_HALF_DOWN));
        num = num.remainder(BigDecimal.valueOf(200));
        setContText();
    }

    public void incrementarAutoSpeed(View v) {
        if (num.longValue() >= 400) {
            tiempoAutoClick = (int) (tiempoAutoClick / 1.5);
            num = num.subtract(BigDecimal.valueOf(400));
            setContText();
        }
    }

    public void incrementarAutoSpeedTotal(View v) {
        if (num.longValue() >= 400) {
            int times = num.divide(BigDecimal.valueOf(400)).intValue();
            tiempoAutoClick = (int) (tiempoAutoClick / (1.5 * times));
            num = num.remainder(BigDecimal.valueOf(400));
            setContText();
        }
    }

    public void incrementar(View v) {
        if (num.longValue() >= 100) {
            inc = inc.add(BigDecimal.valueOf(1));
            num = num.subtract(BigDecimal.valueOf(100));
            setContText();
        }
    }

    public void incrementarAuto(View v) {
        if (num.longValue() >= 200) {
            incAuto = incAuto.add(BigDecimal.valueOf(1)).setScale(0, BigDecimal.ROUND_DOWN);
            num = num.subtract(BigDecimal.valueOf(200));
            setContText();
        }
    }
}