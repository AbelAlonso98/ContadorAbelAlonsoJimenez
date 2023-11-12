package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.SeekBar;

import java.util.Arrays;
import java.util.List;

public class Options extends AppCompatActivity {

    SeekBar volumeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        List<ItemOpcion> itemsOpciones = Arrays.asList(
                new ItemOpcion("Titulo1", "Descripcion1",R.drawable.img_option_orange, this::irAPantallaInicio),
                new ItemOpcion("Titulo2", "Descripcion2",R.drawable.img_option_orange, this::irAPantallaInicio),
                new ItemOpcion("Titulo3", "Descripcion3",R.drawable.img_option_orange, this::irAPantallaInicio),
                new ItemOpcion("Titulo4", "Descripcion4",R.drawable.img_option_orange, this::irAPantallaInicio)
        );
        RecyclerView dataList = findViewById(R.id.recyclerDataList);
        dataList.setHasFixedSize(true);
        dataList.setLayoutManager(new LinearLayoutManager(this));
        dataList.setAdapter(new ItemOpcionAdapter(itemsOpciones));

        volumeBar = findViewById(R.id.volumeBar);
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumeBar.setMax(maxVolume);
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeBar.setProgress(currVolume);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void irAPantallaInicio(View view) {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
        finish();
    }

    public void cambiarTema(View v) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

}
