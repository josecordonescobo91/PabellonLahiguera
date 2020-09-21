package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main_Administradores_Activity extends AppCompatActivity {

    String usuario;
    TabHost TbH;
    ListView lvAdministradores;
    EditText nick, pass, nombre, apellido1, apellido2, telefono, email, direccion, localidad, provincia;
    Button btAgregar;
    String usuario1, con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__administradores_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);

        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");


        tab1.setIndicator("ADMINISTRADORES");
        tab1.setContent(R.id.ADMINISTRADORES);

        tab2.setIndicator("CREAR ADMINISTRADOR");
        tab2.setContent(R.id.CREAR);



        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);


        lvAdministradores = (ListView) findViewById(R.id.lvAdministradores);
        nick = (EditText) findViewById(R.id.nick);
        pass = (EditText) findViewById(R.id.pass);
        nombre = (EditText) findViewById(R.id.nombre);
        apellido1 = (EditText) findViewById(R.id.apellido1);
        apellido2 = (EditText) findViewById(R.id.apellido2);
        telefono = (EditText) findViewById(R.id.telefono);
        email = (EditText) findViewById(R.id.email);
        direccion = (EditText) findViewById(R.id.direccion);
        localidad = (EditText) findViewById(R.id.localidad);
        provincia = (EditText) findViewById(R.id.provincia);
        btAgregar = (Button) findViewById(R.id.btAgregar);

        String tipo = "administrador";

        obtenerAdministradores("http://jose-cordones.es/app/consultas/obtenerPersona.php?tipo="+tipo);

        btAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario1 = nick.getText().toString();
                con = pass.getText().toString();
                if(!usuario1.isEmpty() || !con.isEmpty()){
                    if (!validarEmail(email.getText().toString())){
                        Toast.makeText(Main_Administradores_Activity.this, "EL CORREO NO ES CORRECTO", Toast.LENGTH_SHORT).show();
                        email.setText("");
                    }else{
                        validarAdministrador("http://jose-cordones.es/app/consultas/comprobar_usuario_registro.php");
                    }

                }else{
                    Toast.makeText(Main_Administradores_Activity.this, "NO SE PERMITE NICK O CONTRASEÑA SIN RELLENAR", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void validarAdministrador (String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Toast.makeText(Main_Administradores_Activity.this, "EL USUARIO YA EXISTE", Toast.LENGTH_SHORT).show();
                    nick.setText("");
                }else{
                    ejecutarServicio("http://jose-cordones.es/app/registros/registrar_usuario.php");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main_Administradores_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nick", usuario1);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void ejecutarServicio(String URL1){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(Main_Administradores_Activity.this, Main_InicioAdministrador_Activity.class);
                Toast.makeText(getApplicationContext(), "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        },new Response.ErrorListener(){
            @Override
            public  void  onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>parametros = new HashMap<String, String>();
                String tipo = "administrador", activo = "si";

                parametros.put("nick", nick.getText().toString());
                parametros.put("pass", pass.getText().toString());
                parametros.put("nombre", nombre.getText().toString());
                parametros.put("apellido1", apellido1.getText().toString());
                parametros.put("apellido2", apellido2.getText().toString());
                parametros.put("tipo", tipo);
                parametros.put("telefono", telefono.getText().toString());
                parametros.put("email", email.getText().toString());
                parametros.put("direccion", direccion.getText().toString());
                parametros.put("localidad", localidad.getText().toString());
                parametros.put("provincia", provincia.getText().toString());
                parametros.put("activo", activo);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void obtenerAdministradores(String URL){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        Intent intent = getIntent();
                        //String usuario = intent.getStringExtra("usuario");
                       // String pista = intent.getStringExtra("pista");
                        CargarListViewAdministradores(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewAdministradores(JSONArray ja) {

        final ArrayList<String> lista = new ArrayList<>();

        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add(ja.getString(i + 0) + "            "+ ja.getString(i + 2) + "            "+ ja.getString(i + 1));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvAdministradores.setAdapter(adaptador);

        lvAdministradores.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_Administradores_Activity.this, Main_Editar_Eliminar_Persona_Activity.class);

                intent.putExtra("objetoData", lista.get(position) );

                startActivity(intent);

            }
        });

    }

}
