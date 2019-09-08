package com.example.jonathan.appciec.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.jonathan.appciec.Adapters.PaperAdapter;
import com.example.jonathan.appciec.Models.FirebaseConnector;
import com.example.jonathan.appciec.Models.Paper;
import com.example.jonathan.appciec.Models.SessionHandler;
import com.example.jonathan.appciec.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.android.volley.VolleyLog.TAG;


public class DivulgacionesActivity extends AppCompatActivity {

    private ArrayList<Paper> mPaperData;
    private ArrayList<Paper> mPaperComplete;
    private PaperAdapter mAdapter;
    private FirebaseConnector fconnector;
    private Map favoritos;
    private SessionHandler shandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.favoritos = new HashMap();
        this.shandler = new SessionHandler(this);
        this.fconnector = new FirebaseConnector();
        if (shandler.estaIniciado()){
            Log.e("e","k");
            readData(new FirebaseCallback() {
                @Override
                public void onCallback(Map listado) {
                    favoritos = listado;
                }
            });
        }
        setContentView(R.layout.activity_divulgaciones);

        SearchView searchView =  findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Initialize the RecyclerView.
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_Investigaciones);


        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mPaperData = new ArrayList<>();
        mPaperComplete = new ArrayList<>();
        initializeData();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new PaperAdapter(this, mPaperData, mPaperComplete,favoritos,fconnector,shandler);

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        // Get the data.

    }

    private void readData(final FirebaseCallback firebaseCallback){
        DatabaseReference data = fconnector.getReff_user().child(shandler.usuarioId()).child("Favoritos");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String id = ds.getKey();
                        String titulo = ((Map)ds.getValue()).get("titulo").toString();
                        favoritos.put(titulo,id);
                    }
                }
                firebaseCallback.onCallback(favoritos);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,databaseError.getMessage());
            }

        });
    }
    private interface FirebaseCallback{
        void onCallback(Map listado);
    }
    private void initializeData() {
        // Clear the existing data (to avoid duplication).
        mPaperData.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.ciec.espol.edu.ec/rest/node/20";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String html = response.getJSONObject("body").getJSONArray("und").getJSONObject(0).getString("safe_value");
                    getPapersPublicados(html);
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error","Server Error");
            }


        });

        requestQueue.add(jsonObjectRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_app,menu);
        SearchView searchView =  findViewById(R.id.searchView);
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @SuppressWarnings("NonAsciiCharacters")
    private void getPapersPublicados(String html){
        Document doc = Jsoup.parse(html);
        for (Element element : doc.select("*")) {
            if (!element.hasText() && element.isBlock()) {
                element.remove();
            }
        }

        //noinspection NonAsciiCharacters
        Elements años = doc.getElementsByTag("h1");


        //noinspection NonAsciiCharacters
        for (Element año:años ){

            Elements ps = año.nextElementSiblings().select("p");
            String titulo;
            String autores;
            String fecha;
            String journal;
            String pais;


            for (int i=0; i<ps.size()-4; i+=4){
                String regex_fecha =  "\\(([a-zA-Z]+,? [0-9]+)\\)";
                String regex_pais = "\\(([a-zA-ZÀ-ÿ ]+)\\)";
                Pattern p_fecha = Pattern.compile(regex_fecha); //(mes, año)
                Pattern p_pais = Pattern.compile(regex_pais); //(pais)
                titulo = ps.get(i).text();
                titulo = titulo.replace("&nbsp;","");

                while(titulo.equals("")){
                    i++;
                    titulo = ps.get(i).text();
                }
                titulo = titulo.replaceAll("[0-9]+\\. "  , "");

                autores = ps.get(i+1).text();
                Matcher m1 = p_fecha.matcher(autores);
                autores = autores.replaceAll(regex_fecha,"" );
                if (m1.find()) {
                    fecha = m1.group(1);
                }else{
                    fecha = "no disponible";
                }
                journal = ps.get(i+2).text();

                Matcher m2 = p_pais.matcher(journal);
                journal = journal.replaceAll(regex_pais,"");
                if (m2.find()) {
                    pais = m2.group(1);
                }else{
                    pais = "no disponible";
                }

                Paper paper = new Paper(titulo,autores, fecha, journal, pais);
                mPaperData.add(paper);
                mPaperComplete.add(paper);
            }
        }
    }
}
