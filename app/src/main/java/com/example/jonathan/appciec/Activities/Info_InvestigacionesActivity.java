package com.example.jonathan.appciec.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.jonathan.appciec.R;

public class Info_InvestigacionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_investigaciones);

        // Initialize the views.
        TextView PaperTitle = findViewById(R.id.titleDetail);
        TextView PaperAuthors = findViewById(R.id.autoresDetail);
        TextView PaperFecha = findViewById(R.id.fechaDetail);
        TextView PaperPais = findViewById(R.id.PaisDetail);
        TextView PaperJournal = findViewById(R.id.JournalDetail);


        // Set the text from the Intent extra.
        PaperTitle.setText(getIntent().getStringExtra("title"));
        PaperAuthors.setText(getIntent().getStringExtra("autores_paper"));
        PaperFecha.setText(getIntent().getStringExtra("fecha_paper"));
        PaperPais.setText(getIntent().getStringExtra("pais_paper"));
        PaperJournal.setText(getIntent().getStringExtra("journal_paper"));


    }
}
