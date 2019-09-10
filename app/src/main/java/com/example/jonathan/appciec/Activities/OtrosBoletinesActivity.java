package com.example.jonathan.appciec.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jonathan.appciec.Adapters.BoletinAdapter;
import com.example.jonathan.appciec.Models.Boletin;
import com.example.jonathan.appciec.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class OtrosBoletinesActivity extends AppCompatActivity {

    private ArrayList<Boletin> lista_boletines_social, lista_boletines_macro= new ArrayList<>();
    private BoletinAdapter mAdapterSocial, mAdapterMacro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros_boletines);

        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        new DataAsyncTask().execute();

        RecyclerView mRecyclerViewSocial = findViewById(R.id.recyclerView_Social_Boletines);
        RecyclerView mRecyclerViewMacro = findViewById(R.id.recyclerView_macro_Boletines);
        // Set the Layout Manager.
        mRecyclerViewSocial.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewMacro.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        lista_boletines_social = new ArrayList<>();
        lista_boletines_macro = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapterSocial = new BoletinAdapter(this, lista_boletines_social);
        mAdapterMacro = new BoletinAdapter(this, lista_boletines_macro);


        mRecyclerViewSocial.setAdapter(mAdapterSocial);
        RecyclerView.ItemDecoration itemDecorationSocial = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerViewSocial.addItemDecoration(itemDecorationSocial);
        mRecyclerViewMacro.setAdapter(mAdapterMacro);
        RecyclerView.ItemDecoration itemDecorationMacro = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerViewMacro.addItemDecoration(itemDecorationMacro);
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
            mAdapterMacro.notifyDataSetChanged();
            mAdapterSocial.notifyDataSetChanged();



        }

    }

    private void initializeData() {
        Document doc = null;
        String mainurl = "http://www.ciec.espol.edu.ec";
        String url = "http://www.ciec.espol.edu.ec/content/otros-boletines";

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            Log.e("tag", e.getMessage(), e);
        }
        Elements articles = doc.select("td");


        for (Element article : articles) {
            try {
                String informacion = article.child(0).child(0).ownText();
                String url_pdf = article.child(1).child(0).child(0).attr("href");
                String url_img = article.child(1).child(0).child(0).child(0).attr("src");
                Boletin bol= new Boletin(informacion,url_pdf,url_img);
                lista_boletines_social.add(bol);

            }catch (NullPointerException exc){
                break;
            }
        }

        Elements articles2 = doc.getElementById("block-views-macreeconomia-block").children();

        for (Element article : articles2) {
            try {
                Element nodo = article.child(0).child(0);
                String informacion = nodo.child(0).child(0).ownText();
                String url_pdf = nodo.child(1).child(0).child(0).attr("href");
                String url_img = nodo.child(1).child(0).child(0).child(0).attr("src");
                Boletin bol= new Boletin(informacion,url_pdf,url_img);
                lista_boletines_macro.add(bol);

            }catch (Exception exc){
                break;
            }
        }
    }
}
