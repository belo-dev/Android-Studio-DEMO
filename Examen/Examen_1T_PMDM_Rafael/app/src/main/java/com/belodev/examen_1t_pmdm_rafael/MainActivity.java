package com.belodev.examen_1t_pmdm_rafael;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
     ImageButton uno, dos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uno = findViewById(R.id.imageButton);
        dos = findViewById(R.id.imageButton2);

        uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividadUno = new Intent(MainActivity.this, ActivityUno.class);
                startActivity(actividadUno);
            }
        });

        dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividadDos = new Intent(MainActivity.this, ActivityDos.class);
                startActivity(actividadDos);
            }
        });
    }
}