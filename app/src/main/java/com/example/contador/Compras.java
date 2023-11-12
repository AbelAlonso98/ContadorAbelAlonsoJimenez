package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Abel Alonso Jiménez - ZTL55769@educastur.es
 */
public class Compras extends AppCompatActivity {
    // Variables utilizadas para el funcionamiento del juego
    BigDecimal monedas;
    BigDecimal incClick;
    BigDecimal incAutoClick;
    int tiempoAutoClick;
    private final int maxNivelUpgradeClick = 10;
    private final int maxNivelUpgradeAutoClick = 5;
    private final int maxNivelUpgradeSpeed = 3;
    BigDecimal precioUpgradeClick = new BigDecimal("100");
    BigDecimal precioUpgradeAutoClick = new BigDecimal("200");
    BigDecimal precioUpgradeSpeed = new BigDecimal("400");
    int nivelUpgradeClick = 0;
    int nivelUpgradeAutoClick = 0;
    int nivelUpgradeSpeed = 0;

    // Variables utilizadas para instanciar los componentes
    Button botonSuma;
    Button botonSumaAuto;
    Button botonAutoSpeed;
    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    TextView textMoneyCount;
    TextView clickUpgradeTitle;
    TextView autoClickUpgradeTitle;
    TextView autoClickSpeedUpgradeTitle;

    // Variables utilizadas para efectos de sonido
    SoundPool soundPool;
    int soundId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            monedas = new BigDecimal(extras.getString("MONEY_COUNT"));
            incClick = new BigDecimal(extras.getString("CLICK_VALUE"));
            incAutoClick = new BigDecimal(extras.getString("AUTOCLICK_VALUE"));
            tiempoAutoClick = extras.getInt("AUTOCLICK_TIME");
            precioUpgradeClick = new BigDecimal(extras.getString("UPGRADE_PRECIO_CLICK"));
            precioUpgradeAutoClick = new BigDecimal(extras.getString("UPGRADE_PRECIO_AUTOCLICK"));
            precioUpgradeSpeed = new BigDecimal(extras.getString("UPGRADE_PRECIO_SPEED"));
            nivelUpgradeClick = extras.getInt("UPGRADE_NIVEL_CLICK");
            nivelUpgradeAutoClick = extras.getInt("UPGRADE_NIVEL_AUTOCLICK");
            nivelUpgradeSpeed = extras.getInt("UPGRADE_NIVEL_SPEED");
        }
        botonSuma = findViewById(R.id.botonMejora);
        botonSumaAuto = findViewById(R.id.botonSumarAuto);
        botonAutoSpeed = findViewById(R.id.botonSumarAutoSpeed);
        textValorClick = (TextView) findViewById(R.id.textValorClick);
        textValorAutoClick = (TextView) findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = (TextView) findViewById(R.id.textVelocidadAutoClick);
        textMoneyCount = (TextView) findViewById(R.id.txtMoneyCount);
        clickUpgradeTitle = findViewById(R.id.tituloMejora);
        autoClickUpgradeTitle = findViewById(R.id.tituloMejora2);
        autoClickSpeedUpgradeTitle = findViewById(R.id.tituloMejora3);
        setContText();

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(this, R.raw.upgrade_sound, 1);
    }


    /**
     * Metodo que te devuelve a la partida (MainActivity) pasando los datos necesarios.
     *
     * @param v (Obligatorio al haberse asignado en un OnClick)
     */
    public void volverAJugar(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MONEY_COUNT", monedas.toString());
        i.putExtra("CLICK_VALUE", incClick.toString());
        i.putExtra("AUTOCLICK_VALUE", incAutoClick.toString());
        i.putExtra("AUTOCLICK_TIME", tiempoAutoClick);
        i.putExtra("UPGRADE_PRECIO_CLICK", precioUpgradeClick.toString());
        i.putExtra("UPGRADE_PRECIO_AUTOCLICK", precioUpgradeAutoClick.toString());
        i.putExtra("UPGRADE_PRECIO_SPEED", precioUpgradeSpeed.toString());
        i.putExtra("UPGRADE_NIVEL_CLICK", nivelUpgradeClick);
        i.putExtra("UPGRADE_NIVEL_AUTOCLICK", nivelUpgradeAutoClick);
        i.putExtra("UPGRADE_NIVEL_SPEED", nivelUpgradeSpeed);
        startActivity(i);
        finish();
    }

    /**
     * Metodo que se encarga de mostrar datos por pantalla, se llama a él cada vez que se incrementa el contador.
     */
    public void setContText() {
        int aux;
        // El texto de los botones es dinamico, se cargan aquí
        if (nivelUpgradeClick < maxNivelUpgradeClick) {
            botonSuma.setText(precioUpgradeClick + "$ ");
        } else {
            botonSuma.setText("MAX");
            botonSuma.setEnabled(false);
            botonSuma.setAlpha(0.65f);
        }
        if (nivelUpgradeAutoClick < maxNivelUpgradeAutoClick) {
            botonSumaAuto.setText(precioUpgradeAutoClick + "$");
        } else {
            botonSumaAuto.setText("MAX");
            botonSumaAuto.setEnabled(false);
            botonSumaAuto.setAlpha(0.65f);
        }
        if (nivelUpgradeSpeed < maxNivelUpgradeSpeed) {
            botonAutoSpeed.setText(precioUpgradeSpeed + "$");
        } else {
            botonAutoSpeed.setText("MAX");
            botonAutoSpeed.setEnabled(false);
            botonAutoSpeed.setAlpha(0.65f);

        }
        // Cargo los indicadores de niveles
        clickUpgradeTitle.setText("Valor del click (" + nivelUpgradeClick + "/" + maxNivelUpgradeClick + ")");
        autoClickUpgradeTitle.setText("Valor del auto-click (" + nivelUpgradeAutoClick + "/" + maxNivelUpgradeAutoClick + ")");
        autoClickSpeedUpgradeTitle.setText("AutoClick speed (" + nivelUpgradeSpeed + "/" + maxNivelUpgradeSpeed + ")");

        // Cargo los datos de la barra superior
        textValorClick.setText("Click: " + incClick.toString());
        textValorAutoClick.setText("Autoclick: " + incAutoClick.toString());
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

        if (monedas.compareTo(VALORES.get("K")) < 0)
            textMoneyCount.setText(monedas.toString());
        else {
            for (String s : VALORES.keySet()) {
                if (monedas.compareTo(VALORES.get(s)) >= 0)
                    textMoneyCount.setText(monedas.divide(VALORES.get(s)).setScale(2, RoundingMode.HALF_EVEN).toString() + s);
            }
        }
    }

    /**
     * Metodo que aumenta en uno el incremento del click.
     *
     * @param v (Obligatorio al haberse asignado en un OnClick)
     */
    public void mejorarClick(View v) {
        if (monedas.compareTo(precioUpgradeClick) >= 0 && nivelUpgradeClick < maxNivelUpgradeClick) {
            incClick = incClick.add(BigDecimal.valueOf(1));
            monedas = monedas.subtract(precioUpgradeClick);
            nivelUpgradeClick++;
            precioUpgradeClick = precioUpgradeClick.multiply(BigDecimal.valueOf(2));
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    /**
     * Metodo que aumenta en uno el incremento del AutoClick.
     *
     * @param v (Obligatorio al haberse asignado en un OnClick)
     */
    public void mejorarAutoClick(View v) {
        if (monedas.compareTo(precioUpgradeAutoClick) >= 0 && nivelUpgradeAutoClick < maxNivelUpgradeAutoClick) {
            incAutoClick = incAutoClick.add(BigDecimal.valueOf(1));
            monedas = monedas.subtract(precioUpgradeAutoClick);
            nivelUpgradeAutoClick++;
            precioUpgradeAutoClick = precioUpgradeAutoClick.multiply(BigDecimal.valueOf(2));
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    /**
     * Metodo que aumenta la velocidad del AutoClick.
     *
     * @param v (Obligatorio al haberse asignado en un OnClick)
     */
    public void mejorarAutoClickSpeed(View v) {
        if (monedas.compareTo(precioUpgradeSpeed) >= 0 && nivelUpgradeSpeed < maxNivelUpgradeSpeed) {
            monedas = monedas.subtract(precioUpgradeSpeed);
            tiempoAutoClick = (int) (tiempoAutoClick / 1.25);
            nivelUpgradeSpeed++;
            precioUpgradeSpeed = precioUpgradeSpeed.multiply(BigDecimal.valueOf(3));
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }


}