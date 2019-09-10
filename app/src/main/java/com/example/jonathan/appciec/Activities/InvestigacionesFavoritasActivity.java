package com.example.jonathan.appciec.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jonathan.appciec.Adapters.PaperAdapter;
import com.example.jonathan.appciec.Adapters.PaperFavoritoAdapter;
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


public class InvestigacionesFavoritasActivity extends AppCompatActivity {

    private ArrayList<Paper> mPaperData;
    private ArrayList<Paper> mPaperComplete;
    private PaperFavoritoAdapter mAdapter;
    private FirebaseConnector fconnector;
    private Map favoritos;
    private SessionHandler shandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fconnector = new FirebaseConnector();
        this.favoritos = new HashMap();
        this.shandler = new SessionHandler(this);
        mAdapter = null;
        if (this.shandler.estaIniciado()){
            readData(new FirebaseCallback() {
                @Override
                public void onCallback(Map listado) {
                    favoritos = listado;
                }
            });
        }
        setContentView(R.layout.activity_investigaciones_favoritas);
        SearchView searchView =  findViewById(R.id.searchView_fav);
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
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_Investigaciones_fav);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mPaperData = new ArrayList<>();
        mPaperComplete = new ArrayList<>();
        initializeData();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new PaperFavoritoAdapter(this, mPaperData, mPaperComplete, favoritos, fconnector,shandler);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    private void readData(final FirebaseCallback firebaseCallback){
        DatabaseReference data = fconnector.getReff_user().child(this.shandler.usuarioId()).child("Favoritos");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    favoritos.clear();
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
    private void initializeData(){
        // Clear the existing data (to avoid duplication).
        mPaperData.clear();
        getPapersFavoritos();


    }
    private void getPapersFavoritos(){
        DatabaseReference data = fconnector.getReff_user().child(this.shandler.usuarioId()).child("Favoritos");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String titulo = ((Map)ds.getValue()).get("titulo").toString();
                        String autores = ((Map)ds.getValue()).get("autores").toString();
                        String fecha = ((Map)ds.getValue()).get("fecha").toString();
                        String journal = ((Map)ds.getValue()).get("journal").toString();
                        String pais = ((Map)ds.getValue()).get("pais").toString();
                        String articleLink = ((Map)ds.getValue()).get("articleLink").toString();
                        String journalLink = ((Map)ds.getValue()).get("link_journalOverview").toString();

                        Paper paper = new Paper(titulo, articleLink,autores, fecha, journal, pais, journalLink);
                        mPaperData.add(paper);
                        mPaperComplete.add(paper);

                    }
                }
                while(mAdapter==null){
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,databaseError.getMessage());
            }
        });

    }
}
