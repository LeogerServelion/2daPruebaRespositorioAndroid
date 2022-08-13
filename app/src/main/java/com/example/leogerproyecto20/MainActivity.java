package com.example.leogerproyecto20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLData;

public class MainActivity extends AppCompatActivity {
private EditText etpatente, etmarca, etmodelo, etprecio;
private AdminSQLiteOpenHelper admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etpatente=findViewById(R.id.etpatente);
        etmarca=findViewById((R.id.etmarca));
        etmodelo=findViewById(R.id.etmodelo);
        etprecio=findViewById(R.id.etprecio);
        admin= new AdminSQLiteOpenHelper(this, "bd1",null, 1);

    }

    public void agregar (View v){
        SQLiteDatabase sqLiteDatabase= admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("patente", etpatente.getText().toString());
        registro.put("marca", etmarca.getText().toString());
        registro.put("modelo", etmodelo.getText().toString());
        registro.put("precio", etprecio.getText().toString());
        sqLiteDatabase.insert("vehiculos", null, registro);
        etpatente.setText("");
        etmarca.setText("");
        etmodelo.setText("");
        etprecio.setText("");
        sqLiteDatabase.close();

        Toast.makeText(this, "Se ha almacenanado el vehiculo", Toast.LENGTH_SHORT).show();
    }

    public void consultar(View v){
        SQLiteDatabase sqLiteDatabase= admin.getWritableDatabase();
        String patente= etpatente.getText().toString();
        Cursor fila=sqLiteDatabase.rawQuery("select marca,modelo,precio from vehiculos where patente='"+patente+"'", null);
        if (fila.moveToFirst()){
            etmarca.setText(fila.getString(0));
            etmodelo.setText(fila.getString(1));
            etprecio.setText(fila.getString(2));

        }

        else{
            Toast.makeText(this, "No existe vehiculo de dicha patente", Toast.LENGTH_SHORT).show();
            etpatente.setText("");
            etmarca.setText("");
            etmodelo.setText("");
            etprecio.setText("");
            sqLiteDatabase.close();
        }

    }

    public void borrado(View v){
        SQLiteDatabase sqLiteDatabase= admin.getWritableDatabase();
        String patente= etpatente.getText().toString();
        int cant=sqLiteDatabase.delete("vehiculos", "patente='"+patente+"'", null);
        if (cant==1){
            Toast.makeText(this, "Se eliminó el vehiculo", Toast.LENGTH_SHORT).show();
            etpatente.setText("");
            etmarca.setText("");
            etmodelo.setText("");
            etprecio.setText("");

        }

        else{
            Toast.makeText(this, "No existe vehiculo de dicha patente", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.close();
        }

    }

    public void modificar(View v){
        SQLiteDatabase bd= admin.getWritableDatabase();
        String patente= etpatente.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("marca", etmarca.getText().toString());
        registro.put("modelo", etmodelo.getText().toString());
        registro.put("precio", etprecio.getText().toString());


        int cant=bd.update("vehiculos",registro, "patente='"+patente+"'", null);
        if (cant==1){
            Toast.makeText(this, "Se Mdificaron los datos", Toast.LENGTH_SHORT).show();


        }

        else{
            Toast.makeText(this, "No existe vehiculo de dicha patente", Toast.LENGTH_SHORT).show();
            bd.close();
        }

    }
}