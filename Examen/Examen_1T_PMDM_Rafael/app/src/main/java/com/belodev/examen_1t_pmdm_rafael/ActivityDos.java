package com.belodev.examen_1t_pmdm_rafael;

import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Timer;

public class ActivityDos extends AppCompatActivity {
    Button start, stop, reset;
    TextView contador;
    SharedPreferences prefs;
    int minutos = 0, segundos = 0, decimas = 0, centesimas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dos);
        start = findViewById(R.id.buttonStart);
        stop = findViewById(R.id.buttonStop);
        reset = findViewById(R.id.buttonReset);
        contador = findViewById(R.id.textViewReloj);
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        segundos = prefs.getInt("segundos", 0);
        minutos = prefs.getInt("minutos", 0);
        decimas = prefs.getInt("decimas", 0);
        centesimas = prefs.getInt("centesimas", 0);
        contador.setText(prefs.getString("contador", "00:00.00"));
        CountDownTimer tmp = new CountDownTimer(3600000, 10) {
            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long l) {
                    centesimas++;
                    if(centesimas == 10){
                        decimas++;
                        centesimas = 0;
                    }
                    if(decimas == 10){
                        segundos++;
                        decimas = 0;
                    }
                    if(segundos == 60){
                        segundos = 0;
                        minutos++;
                    }
                    prefs.edit().putInt("segundos", segundos).apply(); // guarda el nuevo valor
                    prefs.edit().putInt("minutos", minutos).apply(); // guarda el nuevo valor
                    prefs.edit().putInt("decimas", decimas).apply(); // guarda el nuevo valor
                    prefs.edit().putInt("centesimas", centesimas).apply(); // guarda el nuevo valor
                    if(segundos < 10 && minutos < 10){
                        contador.setText("0"+minutos+":0"+segundos+"."+decimas+centesimas);
                    }
                    if(segundos >= 10 && minutos < 10){
                    contador.setText("0"+minutos+":"+segundos+"."+decimas+centesimas);
                    }
                    if(segundos < 10 && minutos >= 10){
                    contador.setText(minutos+":0"+segundos+"."+decimas+centesimas);
                    }
                    if(segundos >= 10 && minutos >= 10){
                    contador.setText(minutos+":"+segundos+"."+decimas+centesimas);
                    }
                    prefs.edit().putString("contador", contador.getText().toString()).apply(); // guarda el nuevo valor
                }
        };


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp.cancel();
                tmp.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp.cancel();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp.cancel();
                minutos = 0;
                segundos = 0;
                decimas = 0;
                centesimas = 0;
                contador.setText("00:00.00");
            }
        });
    }
}