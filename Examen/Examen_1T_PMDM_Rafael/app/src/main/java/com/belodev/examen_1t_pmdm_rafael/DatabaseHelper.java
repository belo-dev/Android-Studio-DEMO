package com.belodev.examen_1t_pmdm_rafael;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Nombre del archivo físico de la base de datos
    private static final String DB_NAME = "votos.db";
    // Versión de la base de datos (si la incrementas, se ejecuta onUpgrade)
    private static final int DB_VERSION = 1;

    // Nombre de la tabla y columnas
    public static final String TABLE_NAME = "paises";
    public static final String COL_NAME = "nombre";
    public static final String COL_VALUE = "votos";

    // Constructor: recibe el contexto y crea/abre la BD
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Se ejecuta la primera vez que se crea la BD
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Sentencia SQL para crear la tabla con sus columnas
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_NAME + " TEXT PRIMARY KEY, " +                 // Nombre (texto)
                        COL_VALUE + " REAL)"                              // Valor (número real)
        );
        db.execSQL("INSERT INTO paises(nombre, votos)\n" +
                "VALUES ('Reino Unido', 0),\n" +
                "       ('España', 0),\n" +
                "       ('Islandia', 0),\n" +
                "       ('Dinamarca', 0),\n" +
                "       ('Luxemburgo', 0)");
    }

    // Se ejecuta cuando incrementas DB_VERSION (sirve para actualizar la estructura de la BD)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Borra la tabla existente y la vuelve a crear
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ✅ Insertar un registro en la tabla
    public long insertItem(String name, double value) {
        SQLiteDatabase db = getWritableDatabase(); // abre la BD en modo escritura
        ContentValues values = new ContentValues(); // contenedor de valores
        values.put(COL_NAME, name);   // asigna el nombre
        values.put(COL_VALUE, value); // asigna el valor
        // Inserta en la tabla y devuelve el ID generado o -1 si falla
        return db.insert(TABLE_NAME, null, values);
    }

    // ✅ Obtener todos los registros de la tabla
    public ArrayList<String> getAllItems() {
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase(); // abre la BD en modo lectura
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null); // consulta todos los registros
        while (cursor.moveToNext()) {
            // Extrae cada columna del registro actual
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
            int value = cursor.getInt(cursor.getColumnIndexOrThrow(COL_VALUE));
            // Construye un String con el formato "id - nombre (valor)"
            lista.add(name + ": " + value);
        }
        cursor.close(); // cierra el cursor
        return lista;   // devuelve la lista con todos los registros
    }

    // ✅ Actualizar un registro por su ID
    public void updateItem(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE paises SET votos = votos + 1 WHERE nombre = '" + name + "'");

    }
}
