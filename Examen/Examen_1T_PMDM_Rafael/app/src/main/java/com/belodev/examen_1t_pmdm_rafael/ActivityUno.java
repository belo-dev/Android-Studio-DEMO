package com.belodev.examen_1t_pmdm_rafael;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityUno extends AppCompatActivity {
    RadioGroup grupo;
    Button votarBtn;
    ListView votosLv;
    DatabaseHelper db;                  // Helper para manejar la base de datos SQLite
    ArrayAdapter<String> adapter;       // Adaptador para conectar la lista de Strings con el ListView
    ArrayList<String> items;            // Lista que contendrá los registros en formato texto
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uno);
        grupo = findViewById(R.id.grupoRb);
        votarBtn = findViewById(R.id.votarBtn);
        votosLv = findViewById(R.id.lv);

        db = new DatabaseHelper(this);


        // Obtenemos todos los registros de la BD y los guardamos en la lista
        items = db.getAllItems();

        // Creamos el adaptador para mostrar los registros en el ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        votosLv.setAdapter(adapter);

        votarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String paisVotado = obtenerVoto(grupo.getCheckedRadioButtonId());
               db.updateItem(paisVotado);
               onResume();
            }
        });
    }
    private String obtenerVoto(int id) {
        if (id == R.id.ru) return "Reino Unido";
        if (id == R.id.es ) return "España";
        if (id == R.id.is)  return "Islandia";
        if (id == R.id.di)  return "Dinamarca";
        if (id == R.id.lu)  return "Luxemburgo";
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cada vez que volvemos a esta Activity, refrescamos la lista
        items.clear();                   // Limpiamos la lista actual
        items.addAll(db.getAllItems());  // Volvemos a cargar desde la BD
        adapter.notifyDataSetChanged();  // Notificamos al adaptador para actualizar la vista
    }
}