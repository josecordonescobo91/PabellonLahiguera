package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button login;
    Button horario;
    Button precios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    //Metodo para ir a la pantalla login
    public void Login (View view){
        Intent login = new Intent(this, Main_Login_Activity.class);
        startActivity(login);
    }

    public void Horario (View view){
        Intent horario = new Intent(this, Main_Horario_Activity.class);
        Intent intent = new Intent(getApplicationContext(), Main_Horario_Activity.class);
        startActivity(horario);
    }

    public void Precios (View view){
        Intent precios = new Intent(this, Main_Precios_Activity.class);
        startActivity(precios);
    }

    public void RegistroUsuaruio (View view){
        Intent RegistroUsuario = new Intent(this, Main_RegistrarUsuario_Activity.class);
        startActivity(RegistroUsuario);
    }
}
