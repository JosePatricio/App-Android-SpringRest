package com.android.aplicacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.utiles.Api;
import com.android.utiles.Persona;
import com.android.utiles.RequestHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final int CODE_DELETE_REQUEST = 1026;

    private EditText txtCedulaPersona;
    private ProgressBar progressBar;
    private ListView listView;
    private Button btnConsultar, btnCrear,btnCargarTodos;

    private List<Persona> lstPersona;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCedulaPersona = (EditText) findViewById(R.id.txtCedulaPersona);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnCrear = (Button) findViewById(R.id.btnCrearNuevo);
        btnCargarTodos = (Button) findViewById(R.id.btnCargarTodos);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewPersonas);
        lstPersona = new ArrayList<>();


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        btnCargarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarPersonas();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarPorCedula();
            }
        });
        consultarPersonas();
    }

    /**
     * Método para consultar Persona por cédula
     */
    private void consultarPorCedula() {
        String cedula = txtCedulaPersona.getText().toString().trim();

        if (TextUtils.isEmpty(cedula)) {
            txtCedulaPersona.setError("Ingrese cédula");
            txtCedulaPersona.requestFocus();
            return;
        }

        MainActivity.PerformNetworkRequest request = new MainActivity.PerformNetworkRequest(Api.URL_READ_CLIENTE_RUC + cedula,
                null, CODE_GET_REQUEST);
        request.execute();


    }

    /**
     * Método para consultar todas las personas
     */
    private void consultarPersonas() {

        MainActivity.PerformNetworkRequest request = new MainActivity.PerformNetworkRequest(Api.URL_READ_PERSONAS,
                null, CODE_GET_REQUEST);
        request.execute();


    }

    /***
     * Método para eliminar persona
     * @param id
     */
    private void eliminarPersona(int id) {
        MainActivity.PerformNetworkRequest request = new MainActivity.PerformNetworkRequest(Api.URL_DELETE_PERSONA + id, null, CODE_DELETE_REQUEST);
        request.execute();
    }

    private void cargarLista(JSONArray personas) throws JSONException {
        lstPersona.clear();
        Persona persona = null;

        for (int i = 0; i < personas.length(); i++) {
            JSONObject obj = personas.getJSONObject(i);
            persona = new Persona();
            persona.setId(Integer.parseInt(obj.getString("id").trim()));
            persona.setNombre(obj.getString("nombre"));
            persona.setApellido(obj.getString("apellido"));
            persona.setRuc(obj.getInt("ruc"));
            persona.setDireccion(obj.getString("direccion"));
            persona.setTelefono(obj.getString("telefono"));
            persona.setImagen(obj.getString("imagen"));
            lstPersona.add(persona);
        }

        MainActivity.ListaAdapter adapter = new MainActivity.ListaAdapter(lstPersona);
        listView.setAdapter(adapter);
    }

    /**
     * Clase asincrono para hacer peticiones http al servicio Rest
     */
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
            }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            if (requestCode == CODE_DELETE_REQUEST)
                return requestHandler.getDeleteDataString(url);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.isEmpty()) {
                return;
            }

            progressBar.setVisibility(GONE);
            try {
                JSONArray jsonarray = new JSONArray(s);
                cargarLista(jsonarray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clase para adaptar el layout de la lista
     */
    class ListaAdapter extends ArrayAdapter<Persona> {
        List<Persona> lstPersonas;


        public ListaAdapter(List<Persona> lstPersona) {
            super(MainActivity.this,
                    R.layout.layout_persona_list, lstPersona);
            this.lstPersonas = lstPersona;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_persona_list, null, true);
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);
            TextView textViewPersona = listViewItem.findViewById(R.id.txtView);


            final Persona persona = lstPersonas.get(position);
            textViewName.setText(persona.getNombre() + " " + persona.getApellido());
            textViewPersona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
                    intent.putExtra("perfil", persona);
                    MainActivity.this.startActivity(intent);
                }
            });


            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Eliminar " + persona.getNombre())
                            .setMessage("Está seguro de eliminar?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    eliminarPersona(persona.getId());
                                    consultarPersonas();
                                    Toast.makeText(getBaseContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                            }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }
}
