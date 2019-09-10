package com.example.jonathan.appciec.Fragments;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jonathan.appciec.Models.Evento;
import com.example.jonathan.appciec.Adapters.EventoAdapter;
import com.example.jonathan.appciec.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EventosFragment extends Fragment {
    private ArrayList<Evento> mNoticiasData;
    private EventoAdapter mAdapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_eventos, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        RecyclerView mRecyclerView = view.findViewById(R.id.cafe_ciec);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mNoticiasData = new ArrayList<>();

        mAdapter = new EventoAdapter(this.mNoticiasData, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        new DataAsyncTask().execute();

    }

    class DataAsyncTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params)
        {
            initializeData();
            return "executed";
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);

            mAdapter.notifyDataSetChanged();


        }

    }
/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
//        setContentView(R.layout.fragment_eventos);
//
//        // Initialize the RecyclerView.
//        // Member variables.
//        RecyclerView mRecyclerView = findViewById(R.id.cafe_ciec);
//
//        // Set the Layout Manager.
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Initialize the ArrayList that will contain the data.
//        this.mNoticiasData = new ArrayList<>();
//
//        // Initialize the adapter and set it to the RecyclerView.
//        mAdapter = new EventoAdapter(this.mNoticiasData, this);
//        mRecyclerView.setAdapter(mAdapter);
//
//        // Get the data.
//        initializeData();
//
//        // Definimos el webView
//        browser=(WebView)findViewById(R.id.visorweb);
//
//        //Habilitamos JavaScript
//        browser.getSettings().setJavaScriptEnabled(true);
//
//        //Habilitamos los botones de Zoom
//        browser.getSettings().setBuiltInZoomControls(true);
//
//        browser.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//
//        // Cargamos la web
//        browser.loadUrl("http://www.ciec.espol.edu.ec/sites/default/files/imagenes/CAFE-CIEC_WEB-FB.jpg");
//
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
*/
    private void initializeData() {
        Document doc = null;
        String mainurl = "http://www.ciec.espol.edu.ec";
        String url = "http://www.ciec.espol.edu.ec/eventos";

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            Log.e("tag", e.getMessage(), e);
        }
        Elements articles = doc.select("td");

        for (Element article : articles) {
            try {
                //String informacion = article.ownText();
                String url_enlace = article.getElementsByClass("views-field views-field-field-imagen-evento-front").first().child(0).getElementsByTag("a").attr("href");
                Document doc_evento = null;
                String url_evento = mainurl + url_enlace;

                try {
                    doc_evento = Jsoup.connect(url_evento).get();
                } catch (IOException e) {
                    Log.e("tag", e.getMessage(), e);
                }
                Element article_evento = doc_evento.getElementsByClass("field-item").first();
                String url_img = mainurl + article_evento.child(0).child(0).attr("src");
                Evento eve= new Evento(url_img);
                mNoticiasData.add(eve);

            }catch (NullPointerException exc){
                break;
            }
        }
    }

}


