package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
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
    BigDecimal num;
    BigDecimal inc;
    BigDecimal incAuto;
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
    int nivelPosibleUpgradeClick;
    int nivelPosibleUpgradeAutoClick;
    int nivelPosibleUpgradeSpeed;

    // Variables utilizadas para instanciar los componentes
    Button botonSuma;
    Button botonSumaAuto;
    Button botonAutoSpeed;
    Button botonSumaTotal;
    Button botonSumarAutoTotal;
    Button botonSumarAutoSpeedTotal;
    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    TextView textMoneyCount;
    TextView clickUpgradeTitle;
    TextView autoClickUpgradeTitle;
    TextView autoClickSpeedUpgradeTitle;

    SoundPool soundPool;
    int soundId;


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
        botonSumaTotal = (Button) findViewById(R.id.botonMejoraTotal);
        botonSumarAutoTotal = (Button) findViewById(R.id.botonSumarAutoTotal);
        botonSumarAutoSpeedTotal = (Button) findViewById(R.id.botonSumarAutoSpeedTotal);
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


    public void volverAJugar(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MONEY_COUNT", num.toString());
        i.putExtra("CLICK_VALUE", inc.toString());
        i.putExtra("AUTOCLICK_VALUE", incAuto.toString());
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

    public void setContText() {
        int aux;
        // El texto de los botones es dinamico, se cargan aquí
        if (nivelUpgradeClick < maxNivelUpgradeClick) {
            aux = calcularCuantosNivelesPuedoComprarUpgradeClick();
            botonSuma.setText(precioUpgradeClick + "$ " + calcularCuantosNivelesPuedoComprarUpgradeClick());
            if (aux == 0)
                botonSumaTotal.setText(precioUpgradeClick + "$ x" + aux);
            else
                botonSumaTotal.setText(calcularCuantoCuestaUpgradearClick(aux).toString() + "$ x" + aux);
        } else {
            botonSuma.setText("MAX");
            botonSumaTotal.setText("MAX");
            botonSuma.setEnabled(false);
            botonSumaTotal.setEnabled(false);
            botonSuma.setAlpha(0.65f);
            botonSumaTotal.setAlpha(0.65f);
        }
        if (nivelUpgradeAutoClick < maxNivelUpgradeAutoClick) {
            botonSumaAuto.setText(precioUpgradeAutoClick + "$");
            botonSumarAutoTotal.setText(precioUpgradeAutoClick + "$ x" + calcularCuantosNivelesPuedoComprarUpgradeAutoClick());
        } else {
            botonSumaAuto.setText("MAX");
            botonSumarAutoTotal.setText("MAX");
            botonSumaAuto.setEnabled(false);
            botonSumarAutoTotal.setEnabled(false);
            botonSumaAuto.setAlpha(0.65f);
            botonSumarAutoTotal.setAlpha(0.65f);
        }
        if (nivelUpgradeSpeed < maxNivelUpgradeSpeed) {
            botonAutoSpeed.setText(precioUpgradeSpeed + "$");
            botonSumarAutoSpeedTotal.setText(precioUpgradeSpeed + "$ x" + calcularCuantosNivelesPuedoComprarUpgradeAutoClickSpeed());
        } else {
            botonAutoSpeed.setText("MAX");
            botonSumarAutoSpeedTotal.setText("MAX");
            botonAutoSpeed.setEnabled(false);
            botonSumarAutoSpeedTotal.setEnabled(false);
            botonAutoSpeed.setAlpha(0.65f);
            botonSumarAutoSpeedTotal.setAlpha(0.65f);
        }
        // Cargo los indicadores de niveles
        clickUpgradeTitle.setText("Valor del click (" + nivelUpgradeClick + "/" + maxNivelUpgradeClick + ")");
        autoClickUpgradeTitle.setText("Valor del auto-click (" + nivelUpgradeAutoClick + "/" + maxNivelUpgradeAutoClick + ")");
        autoClickSpeedUpgradeTitle.setText("AutoClick speed (" + nivelUpgradeSpeed + "/" + maxNivelUpgradeSpeed + ")");

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

    // Metodos para botones de la primera mejora (Click value)
    public void mejorarClick(View v) {
        if (num.compareTo(precioUpgradeClick) >= 0 && nivelUpgradeClick < maxNivelUpgradeClick) {
            inc = inc.add(BigDecimal.valueOf(1));
            num = num.subtract(precioUpgradeClick);
            nivelUpgradeClick++;
            nivelPosibleUpgradeClick = maxNivelUpgradeClick - nivelUpgradeClick;
            precioUpgradeClick = precioUpgradeClick.multiply(BigDecimal.valueOf(2));
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    public void mejorarClickTotal(View v) {
        if (num.compareTo(precioUpgradeClick) >= 0 && nivelUpgradeClick < maxNivelUpgradeClick) {
            int niveles = calcularCuantosNivelesPuedoComprarUpgradeAutoClick();
            inc = inc.add(new BigDecimal(niveles));
            precioUpgradeClick = calcularCuantoCuestaUpgradearClick(niveles);
            nivelUpgradeClick += niveles;
            num = num.subtract(precioUpgradeClick);
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    private int calcularCuantosNivelesPuedoComprarUpgradeClick() {
        if (precioUpgradeClick.compareTo(num) > 0)
            return 0;
        int aux = 0;
        BigDecimal precioAux = precioUpgradeClick;
        BigDecimal precioAux2 = precioUpgradeClick;
        for (int i = nivelUpgradeClick; i <= maxNivelUpgradeClick; i++) {
            if (precioAux2.compareTo(num) > 0) {
                aux++;
                precioAux = precioAux.multiply(BigDecimal.valueOf(2));
                precioAux2 = precioAux2.add(precioAux);
            } else {
                return aux;
            }
        }
        return aux;
    }

    private BigDecimal calcularCuantoCuestaUpgradearClick(int lvl) {
        return precioUpgradeClick.multiply(BigDecimal.valueOf(Math.pow(2, lvl - 1))).setScale(0);
    }


    // Metodos para botones de la segunda mejora (AutoClick value)
    public void mejorarAutoClick(View v) {
        if (num.compareTo(precioUpgradeAutoClick) >= 0 && nivelUpgradeAutoClick < maxNivelUpgradeAutoClick) {
            incAuto = incAuto.add(BigDecimal.valueOf(1));
            num = num.subtract(precioUpgradeAutoClick);
            nivelUpgradeAutoClick++;
            nivelPosibleUpgradeAutoClick--;
            precioUpgradeAutoClick = precioUpgradeAutoClick.multiply(BigDecimal.valueOf(2));
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    public void mejorarAutoClickTotal(View v) {
        if (nivelUpgradeAutoClick < maxNivelUpgradeAutoClick) {
            incAuto = incAuto.add(new BigDecimal(calcularCuantosNivelesPuedoComprarUpgradeAutoClick()));
            // Se basa en que el precio total por nivel es precioActual*(2^numeroNivelesASubir)
            num = num.subtract(precioUpgradeAutoClick.multiply(BigDecimal.valueOf(Math.pow(2, calcularCuantosNivelesPuedoComprarUpgradeAutoClick())))).setScale(0);
            nivelUpgradeAutoClick += calcularCuantosNivelesPuedoComprarUpgradeAutoClick();
            nivelPosibleUpgradeClick = maxNivelUpgradeClick - nivelUpgradeClick;
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    private int calcularCuantosNivelesPuedoComprarUpgradeAutoClick() {
        BigDecimal precioAux = precioUpgradeAutoClick;
        for (int i = 0; i < nivelPosibleUpgradeAutoClick; i++) {
            if (precioAux.compareTo(num) > 0)
                return i;
            precioAux = precioAux.multiply(BigDecimal.valueOf(2));
        }
        return nivelPosibleUpgradeAutoClick;
    }

    // Metodos para botones de la tercera mejora (AutoClick speed)

    public void mejorarAutoClickSpeed(View v) {
        if (num.compareTo(precioUpgradeSpeed) >= 0 && nivelUpgradeSpeed < maxNivelUpgradeSpeed) {
            num = num.subtract(precioUpgradeSpeed);
            tiempoAutoClick = (int) (tiempoAutoClick / 1.25);
            nivelUpgradeSpeed++;
            precioUpgradeSpeed = precioUpgradeSpeed.multiply(BigDecimal.valueOf(3));
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    public void mejorarAutoClickSpeedTotal(View v) {
        if (num.compareTo(precioUpgradeSpeed) >= 0 && nivelUpgradeSpeed < maxNivelUpgradeSpeed) {
            int times = calcularCuantosNivelesPuedoComprarUpgradeAutoClickSpeed();
            tiempoAutoClick = (int) (tiempoAutoClick / (1.5 * times));
            // Se basa en que el precio total por nivel es precioActual*(3^numeroNivelesASubir)
            num = num.subtract(precioUpgradeSpeed.multiply(BigDecimal.valueOf(Math.pow(3, calcularCuantosNivelesPuedoComprarUpgradeAutoClickSpeed())))).setScale(0);
            nivelUpgradeSpeed += calcularCuantosNivelesPuedoComprarUpgradeAutoClickSpeed();
            setContText();
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    private int calcularCuantosNivelesPuedoComprarUpgradeAutoClickSpeed() {
        BigDecimal precioAux = precioUpgradeSpeed;
        for (int i = 0; i < nivelPosibleUpgradeSpeed; i++) {
            if (precioAux.compareTo(num) > 0)
                return i;
            precioAux = precioAux.multiply(BigDecimal.valueOf(3));
        }
        return nivelPosibleUpgradeSpeed;
    }


}