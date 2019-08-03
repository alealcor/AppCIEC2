package com.example.jonathan.appciec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InvestigacionesActivity extends AppCompatActivity {

    private ArrayList<Paper> mPaperData;
    private ArrayList<Paper> mPaperComplete;
    private PaperAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigaciones);
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

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        // Initialize the RecyclerView.
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_Investigaciones);


        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mPaperData = new ArrayList<>();
        mPaperComplete = new ArrayList<>();
        initializeData();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new PaperAdapter(this, mPaperData, mPaperComplete);


        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    private void initializeData() {
        // Clear the existing data (to avoid duplication).
        mPaperData.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.ciec.espol.edu.ec/rest/node/2";
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
    public void getPapersPublicados(String html){
        Document doc = Jsoup.parse(html);
        for (Element element : doc.select("*")) {
            if (!element.hasText() && element.isBlock()) {
                element.remove();
            }
        }

        Elements años = doc.getElementsByTag("h1");

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
