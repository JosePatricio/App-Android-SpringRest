package com.android.aplicacion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.utiles.Api;
import com.android.utiles.Persona;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class RegistroActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap imageBitCamera;
    private Button btnRegistrar, btnCamara;
    private EditText txtNombre, txtApellido, txtCedula, txtDireccion, txtTelefono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnCamara = (Button) findViewById(R.id.btnCamara);
        imageView = (ImageView) findViewById(R.id.image_foto);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellidos);
        txtCedula = (EditText) findViewById(R.id.txtCedula);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = new Persona();
                persona.setNombre(txtNombre.getText().toString());
                persona.setApellido(txtApellido.getText().toString());
                persona.setRuc(Integer.parseInt(txtCedula.getText().toString()));
                persona.setDireccion(txtDireccion.getText().toString());
                persona.setTelefono(txtTelefono.getText().toString());
                persona.setImagen(bitToBase64(imageBitCamera));
                guardar(persona);
            }
        });
    }


    /**
     * Método para guardar los datos de la persona
     *
     * @param persona
     */
    private void guardar(final Persona persona) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("nombre", persona.getNombre());
            jsonBody.put("apellido", persona.getApellido());
            jsonBody.put("ruc", String.valueOf(persona.getRuc()));
            jsonBody.put("direccion", persona.getDireccion());
            jsonBody.put("telefono", persona.getTelefono());
            jsonBody.put("imagen", persona.getImagen());
            jsonBody.put("estado", String.valueOf(true));

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_CREATE_PERSONA, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Toast.makeText(getBaseContext(), "Guardado exitósamete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistroActivity.this, PerfilActivity.class);
                    intent.putExtra("perfil", persona);
                    RegistroActivity.this.startActivity(intent);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistroActivity.this, "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Método para convertir de tipo BitMap a base64
     *
     * @param bitmap
     * @return
     */
    private String bitToBase64(Bitmap bitmap) {

        if (bitmap != null && bitmap.getByteCount() != 0) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            return encoded;
        } else {
            return "";
        }
    }

    /**
     * Captura la imagen
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK && data != null) {
                imageBitCamera = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(imageBitCamera);
            }
        }
    }


}
