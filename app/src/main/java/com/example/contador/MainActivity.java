package com.example.contador;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {
    // Variables utilizadas para el funcionamiento del juego
    BigDecimal monedas = new BigDecimal("0");
    BigDecimal incClick = new BigDecimal("1");
    BigDecimal incAutoClick = new BigDecimal("1");
    BigDecimal precioUpgradeClick = new BigDecimal("100");
    BigDecimal precioUpgradeAutoClick = new BigDecimal("200");
    BigDecimal precioUpgradeSpeed = new BigDecimal("400");
    int nivelUpgradeClick = 1;
    int nivelUpgradeAutoClick = 1;
    int nivelUpgradeSpeed = 1;
    int tiempoAutoClick = 1000; // Milisegundos

    // Variables utilizadas para instanciar los componentes
    TextView contador;
    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    ImageView moneda;

    // Variables utilizadas para efectos de sonido y animaciones
    ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    MediaPlayer mediaPlayer;
    SoundPool soundPool;
    int soundId;
    boolean musicEnabled;
    boolean soundEffectsEnabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cargo los datos (en caso de que los haya) que hayan pasado otras activities.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            monedas = new BigDecimal(extras.getString("MONEY_COUNT", "0"));
            incClick = new BigDecimal(extras.getString("CLICK_VALUE", "0"));
            incAutoClick = new BigDecimal(extras.getString("AUTOCLICK_VALUE", "0"));
            tiempoAutoClick = extras.getInt("AUTOCLICK_TIME");
            precioUpgradeClick = new BigDecimal(extras.getString("UPGRADE_PRECIO_CLICK"));
            precioUpgradeAutoClick = new BigDecimal(extras.getString("UPGRADE_PRECIO_AUTOCLICK"));
            precioUpgradeSpeed = new BigDecimal(extras.getString("UPGRADE_PRECIO_SPEED"));
            nivelUpgradeClick = extras.getInt("UPGRADE_NIVEL_CLICK");
            nivelUpgradeAutoClick = extras.getInt("UPGRADE_NIVEL_AUTOCLICK");
            nivelUpgradeSpeed = extras.getInt("UPGRADE_NIVEL_SPEED");
        }

        //  Cargo todos los componentes que voy a usar.
        contador = findViewById(R.id.textocontador);
        textValorClick = findViewById(R.id.textValorClick);
        textValorAutoClick = findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = findViewById(R.id.textVelocidadAutoClick);
        moneda = findViewById(R.id.moneda);

        // Cargo animaciones, musica y efectos de sonido
        fade_in.setDuration(100);
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(this, R.raw.coin_sound, 1);


        // Cargo el texto con la funcion que lo formatea e inicio el sumar auto.
        setContText();
        sumarAuto();
    }

    /**
     * Metodo que suma el incClick a las monedas. Se activa desde la moneda central.
     *
     * @param v (Obligatorio al haberse asignado en un OnClick)
     */
    public void sumar(View v) {
        monedas = monedas.add(incClick);
        moneda.startAnimation(fade_in);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
        setContText();
    }

    /**
     * Metodo que se encarga de crear el hilo para el ClickAuto.
     */
    public void sumarAuto() {
        new Thread(() -> {
            while (true) {
                monedas = monedas.add(incAutoClick);
                runOnUiThread(this::setContText);
                try {
                    Thread.sleep(tiempoAutoClick);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }


    /**
     * Metodo que se encarga de mostrar datos por pantalla, se llama a él cada vez que se incrementa el contador.
     */
    public void setContText() {

        // Cargo un mapa que contiene el divisor para asignar la letra que tiene como key.
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
            contador.setText(monedas.toString());
        else {
            for (String s : VALORES.keySet()) {
                if (monedas.compareTo(VALORES.get(s)) >= 0)
                    contador.setText(monedas.divide(VALORES.get(s)).setScale(2, RoundingMode.HALF_EVEN).toString() + s);
            }
        }
        textValorClick.setText("Click: " + incClick.toString());
        textValorAutoClick.setText("Autoclick: " + incAutoClick.toString());
        textVelocidadAutoClick.setText("Autoclick speed: " + tiempoAutoClick + "m" + "s");
    }


    /**
     * Metodo que resetea el contador, está asignado en el OnClick del contador y pide confirmacion a traves de un Dialog.
     *
     * @param v (Obligatorio al haberse asignado en un OnClick)
     */
    public void reset(View v) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setCancelable(true);
        constructor.setTitle("Reset");
        constructor.setMessage("¿Seguro que quieres resetear el progreso?");
        constructor.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                monedas = new BigDecimal("0");
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

    /**
     * Método que devuelve a la pantalla de inicio, pide confirmación a través de un Dialog. Se llama a través del icono de back de la esquina superior derecha.
     *
     * @param view (Obligatorio al haberse asignado en un OnClick)
     */
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
        constructor.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = constructor.create();
        dialog.show();

    }

    /**
     * Metodo que crea el activity de PantallaInicio, lo lanza y cierra la actual. Lo uso como auxiliar puesto que no tengo acceso a ello al redefinir el OnClick del boton del dialog.
     */
    public void salir() {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
        finish();
    }

    /**
     * Metodo que crea y abre el activity de Compras, cerrando el actual y pasandole datos que se usarán en ella. Se activara al clickar en el carrito.
     *
     * @param view (Obligatorio al haberse asignado en un OnClick)
     */
    public void irACompras(View view) {
        Intent i = new Intent(this, Compras.class);
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
     * Método redefinido para parar la música al salir de la partida y liberar los recursos que usa el SoundPool.
     */
    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        soundPool.release();
        soundPool = null;
    }
}