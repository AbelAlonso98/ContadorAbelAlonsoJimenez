package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;

public class Compras extends AppCompatActivity {

    BigInteger num;
    BigInteger inc = new BigInteger("1");
    BigInteger incAuto = new BigInteger("1");
    int tiempoAutoClick = 1000;

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
            num = new BigInteger(extras.getString("MONEY_COUNT"));
            inc = new BigInteger(extras.getString("CLICK_VALUE"));
            incAuto = new BigInteger(extras.getString("AUTOCLICK_VALUE"));
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
    }

    public void setContText() {
        botonSumaTotal.setText("100$ x" + num.divide(BigInteger.valueOf(100)));
        botonSumarAutoTotal.setText("200$ x" + num.divide(BigInteger.valueOf(200)));
        botonSumarAutoSpeedTotal.setText("400$ x" + num.divide(BigInteger.valueOf(400)));

        textValorClick.setText("Click: " + inc.toString());
        textValorAutoClick.setText("Autoclick: " + incAuto.toString());
        textVelocidadAutoClick.setText("Autoclick speed: " + tiempoAutoClick + "m" + "s");

        textMoneyCount.setText(num.toString());
    }

    public void sumaTotal(View v) {
        // inc += num/100 and num=num%100
        inc = inc.add(num.divide(BigInteger.valueOf(100)));
        num = num.remainder(BigInteger.valueOf(100));
        setContText();
    }

    public void sumaTotalAuto(View v) {
        incAuto = incAuto.add(num.divide(BigInteger.valueOf(200)));
        num = num.remainder(BigInteger.valueOf(200));
        setContText();
    }
    public void incrementarAutoSpeed(View v) {
        if (num.longValue() >= 400) {
            tiempoAutoClick = (int) (tiempoAutoClick / 1.5);
            num = num.subtract(BigInteger.valueOf(400));
            setContText();
        }
    }

    public void incrementarAutoSpeedTotal(View v) {
        if (num.longValue() >= 400) {
            int times = num.divide(BigInteger.valueOf(400)).intValue();
            tiempoAutoClick = (int) (tiempoAutoClick / (1.5 * times));
            num = num.remainder(BigInteger.valueOf(400));
            setContText();
        }
    }

    public void incrementar(View v) {
        if (num.longValue() >= 100) {
            inc = inc.add(BigInteger.valueOf(1));
            num = num.subtract(BigInteger.valueOf(100));
            setContText();
        }
    }

    public void incrementarAuto(View v) {
        if (num.longValue() >= 200) {
            incAuto = incAuto.add(BigInteger.valueOf(1));
            num = num.subtract(BigInteger.valueOf(200));
            setContText();
        }
    }
}