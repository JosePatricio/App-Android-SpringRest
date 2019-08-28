package com.android.aplicacion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.utiles.Persona;

public class PerfilActivity extends AppCompatActivity {

    TextView txtNombre, txtApellido, txtCedula,txtDireccion,txtTelefono;
    ImageView imageView;
    Button btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent i = getIntent();
        Persona persona = (Persona) i.getSerializableExtra("perfil");

        btnInicio=(Button) findViewById(R.id.btnInicio);
        txtNombre = (TextView) findViewById(R.id.lblNombrep);
        txtApellido = (TextView) findViewById(R.id.lblApellidosp);
        txtCedula = (TextView) findViewById(R.id.lblDnip);
        txtDireccion = (TextView) findViewById(R.id.lblDireccionp);
        txtTelefono = (TextView) findViewById(R.id.lblTelefonop);
        imageView = (ImageView) findViewById(R.id.image_fotop);


        if(persona.getImagen()!=null ){
            byte[] decodedString = Base64.decode(persona.getImagen(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }

        txtNombre.setText(persona.getNombre());
        txtApellido.setText(persona.getApellido());
        txtCedula.setText(persona.getRuc()+"");
        txtDireccion.setText(persona.getDireccion());
        txtTelefono.setText(persona.getTelefono());

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                PerfilActivity.this.startActivity(intent);
            }
        });
    }
}
