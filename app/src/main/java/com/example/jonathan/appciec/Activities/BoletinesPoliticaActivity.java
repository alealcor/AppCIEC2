package com.example.jonathan.appciec.Activities;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.example.jonathan.appciec.Adapters.BoletinAdapter;
import com.example.jonathan.appciec.Models.Boletin;
import com.example.jonathan.appciec.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BoletinesPoliticaActivity extends AppCompatActivity {

    private ArrayList<Boletin> lista_boletines = new ArrayList<>();
    private BoletinAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletin_politica);

        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        new DataAsyncTask().execute();

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_Boletines);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        lista_boletines = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new BoletinAdapter(this, lista_boletines);


        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    class DataAsyncTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            initializeData();
            return "executed";
        }

        @Override
        protected void onPostExecute(String result) {
            mAdapter.notifyDataSetChanged();


        }

    }

    private void initializeData() {
        Document doc = null;
        String mainurl = "http://www.ciec.espol.edu.ec";
        String url = "http://www.ciec.espol.edu.ec/boletin-politica-economica";

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            Log.e("tag", e.getMessage(), e);
        }
        Elements articles = doc.select("td");


        for (Element article : articles) {
            try {
                String informacion = article.ownText();

                String url_pdf = article.getElementsByTag("a").first().attr("href");
                String url_img = article.getElementsByTag("a").first().getElementsByTag("img").attr("src");
                Boletin bol= new Boletin(informacion,url_pdf,url_img);
                lista_boletines.add(bol);

            }catch (NullPointerException exc){
                break;
            }
        }
    }
}
