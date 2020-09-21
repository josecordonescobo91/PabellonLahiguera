package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Main_LoginAdministrador_Activity extends AppCompatActivity {

    EditText edtNick, edtPass;
    Button btnLogin;
    String usuario, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__login_administrador_);

        edtNick = findViewById(R.id.edtNick);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);

        recuperarPreferendiasAdmin();

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                usuario = edtNick.getText().toString();
                password = edtPass.getText().toString();
                if(!usuario.isEmpty() && !password.isEmpty()){
                    validarAdministrador("http://jose-cordones.es/app/validaciones/validar_administrador.php");
                }else{
                    Toast.makeText(Main_LoginAdministrador_Activity.this, "No se permiten campos sin rellenar", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void validarAdministrador (String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    guatdarPreferenciasAdmin();

                    Intent intent = new Intent(getApplicationContext(), Main_InicioAdministrador_Activity.class);
                    intent.putExtra("usuario", usuario);
                    Toast.makeText(Main_LoginAdministrador_Activity.this, "BIENVENIDO " + usuario, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Main_LoginAdministrador_Activity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main_LoginAdministrador_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();

                parametros.put("nick", usuario);
                parametros.put("pass", password);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void guatdarPreferenciasAdmin(){
        SharedPreferences preferencesAdmin = getSharedPreferences("preferenciasLoginAdmin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesAdmin.edit();

        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.putBoolean("sesionAdmin", true);


        editor.commit();
    }



    private void recuperarPreferendiasAdmin(){
        SharedPreferences preferences = getSharedPreferences("preferenciasLoginAdmin", Context.MODE_PRIVATE);
        edtNick.setText(preferences.getString("usuario", ""));
        edtPass.setText(preferences.getString("password", ""));
    }

}
