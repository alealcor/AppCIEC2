package com.example.jonathan.appciec.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jonathan.appciec.R;

public class NoticiaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_detalle);

        // Initialize the views.
        TextView titulo_noticia = findViewById(R.id.titulo_noticia);
        TextView contenido_noticia = findViewById(R.id.contenido_noticia);
        ImageView imagen_noticia = findViewById(R.id.imagen_noticia);

        // Set the text from the Intent extra.
        titulo_noticia.setText(getIntent().getStringExtra("titulo_noticia"));
        contenido_noticia.setText(getIntent().getStringExtra("contenido_noticia"));
        Glide.with(this).load(getIntent().getStringExtra("imagen_noticia")).into(imagen_noticia);


    }
}
