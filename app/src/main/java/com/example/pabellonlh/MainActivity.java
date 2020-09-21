package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    //Metodo para ir a la pantalla login
    public void Login (View view){
        Intent login = new Intent(this, Main_Login_Activity.class);
        startActivity(login);
        finish();
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

    public void LoginAdministrador (View view){
        Intent login = new Intent(this, Main_LoginAdministrador_Activity.class);
        startActivity(login);
        finish();
    }

    public void AlquileresFuturos (View view){
        Intent login = new Intent(this, Main_AlquilerFuturos_Inicio_Activity.class);
        startActivity(login);
    }
}
