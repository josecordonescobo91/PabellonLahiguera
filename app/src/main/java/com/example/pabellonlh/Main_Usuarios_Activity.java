package com.example.pabellonlh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main_Usuarios_Activity extends AppCompatActivity  {

    String usuario;
    TabHost TbH;
    ListView lvUsuarios, lvBusquedaUsuario;
    EditText nick, pass, nombre, apellido1, apellido2, telefono, email, direccion, localidad, provincia;
    Button btAgregar;
    String usuario1, con;
    EditText edFecha;
    Button btnBuscar, btnCancelar;
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "-";
    final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__usuarios_);

        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        intent.putExtra("usuario", usuario);

        TbH = (TabHost) findViewById(R.id.tabHost);
        TbH.setup();

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");


        tab1.setIndicator("USUARIOS");
        tab1.setContent(R.id.USUARIOS);

        tab2.setIndicator("CREAR USUARIO");
        tab2.setContent(R.id.CREAR);


        TbH.addTab(tab1);
        TbH.addTab(tab2);

        edFecha = (EditText) findViewById(R.id.edFecha);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        lvUsuarios = (ListView) findViewById(R.id.lvUsuarios);
        lvBusquedaUsuario = (ListView) findViewById(R.id.lvBusquedaUsuario);
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

        lvBusquedaUsuario.setVisibility(View.INVISIBLE);

        String tipo = "usuario";


        obtenerUsuarios("http://jose-cordones.es/app/consultas/obtenerPersona.php?tipo=" + tipo);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valorSpiner = edFecha.getText().toString();
                if (valorSpiner.equals("")) {
                    Toast.makeText(Main_Usuarios_Activity.this, "DEBE SELECCIONAR UN NICK", Toast.LENGTH_SHORT).show();
                } else {
                    BusquedaNick("http://jose-cordones.es/app/consultas/busquedaUsuarioNick.php?nick=" + valorSpiner);
                    edFecha.setText("");

                    lvUsuarios.setVisibility(View.INVISIBLE);
                    lvBusquedaUsuario.setVisibility(View.VISIBLE);
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvUsuarios.setVisibility(View.VISIBLE);
                lvBusquedaUsuario.setVisibility(View.INVISIBLE);

            }
        });

        btAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario1 = nick.getText().toString();
                con = pass.getText().toString();
                if (!usuario1.isEmpty() || !con.isEmpty()) {
                    if (!validarEmail(email.getText().toString())) {
                        Toast.makeText(Main_Usuarios_Activity.this, "EL CORREO NO ES CORRECTO", Toast.LENGTH_SHORT).show();
                        email.setText("");
                    } else {
                        validarUsuario("http://jose-cordones.es/app/consultas/comprobar_usuario_registro.php");
                    }

                } else {
                    Toast.makeText(Main_Usuarios_Activity.this, "NO SE PERMITE NICK O CONTRASEÑA SIN RELLENAR", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void BusquedaNick(String URL) {


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");

                String response2 = response.replace("][", ",");
                response2 = response2.replaceAll("\n", "");
                response2 = response2.replaceAll("\"", "");
                response2 = response2.replaceAll("\\[", "").replaceAll("\\]", "");

                if (response2.equals("")) {
                    Toast.makeText(Main_Usuarios_Activity.this, "NO HAY NINGUN USUARIO CON ESE NICK", Toast.LENGTH_SHORT).show();
                    lvUsuarios.setVisibility(View.VISIBLE);
                    lvBusquedaUsuario.setVisibility(View.INVISIBLE);

                } else if (response.length() > 0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        Intent intent = getIntent();
                        String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewBusqueda(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewBusqueda(JSONArray ja) {

        final ArrayList<String> lista = new ArrayList<>();


        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add(ja.getString(i + 0) + "            " + ja.getString(i + 2) + "            " + ja.getString(i + 1));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvBusquedaUsuario.setAdapter(adaptador);

        lvBusquedaUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_Usuarios_Activity.this, Main_Editar_Eliminar_Persona_Activity.class);

                intent.putExtra("objetoData", lista.get(position));

                startActivity(intent);

            }


        });
    }


    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(Main_Usuarios_Activity.this, "EL USUARIO YA EXISTE", Toast.LENGTH_SHORT).show();
                    nick.setText("");
                } else {
                    ejecutarServicio("http://jose-cordones.es/app/registros/registrar_usuario.php");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main_Usuarios_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nick", usuario1);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void ejecutarServicio(String URL1) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(Main_Usuarios_Activity.this, Main_InicioAdministrador_Activity.class);
                Toast.makeText(getApplicationContext(), "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                String tipo = "usuario", activo = "si";

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

    public void obtenerUsuarios(String URL) {


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        Intent intent = getIntent();
                        //String usuario = intent.getStringExtra("usuario");
                        String pista = intent.getStringExtra("pista");
                        CargarListViewUsuarios(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListViewUsuarios(JSONArray ja) {

        final ArrayList<String> lista = new ArrayList<>();

        for (int i = 0; i < ja.length(); i += 3) {

            try {
                lista.add(ja.getString(i + 0) + "            " + ja.getString(i + 2) + "            " + ja.getString(i + 1));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        lvUsuarios.setAdapter(adaptador);

        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main_Usuarios_Activity.this, Main_Editar_Eliminar_Persona_Activity.class);

                intent.putExtra("objetoData", lista.get(position));

                startActivity(intent);

            }
        });

    }

}
