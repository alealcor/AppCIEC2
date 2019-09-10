package com.example.jonathan.appciec.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.jonathan.appciec.R;

public class Info_InvestigacionesActivity extends AppCompatActivity {
    private Button btnDownload;
    private Button btnOverview;

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
        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnOverview = (Button) findViewById(R.id.btnoverview);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(getIntent().getStringExtra("link"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(getIntent().getStringExtra("overview_link"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        // Set the text from the Intent extra.
        PaperTitle.setText(getIntent().getStringExtra("title"));
        PaperAuthors.setText(getIntent().getStringExtra("autores_paper"));
        PaperFecha.setText(getIntent().getStringExtra("fecha_paper"));
        PaperPais.setText(getIntent().getStringExtra("pais_paper"));
        PaperJournal.setText(getIntent().getStringExtra("journal_paper"));


    }
}
