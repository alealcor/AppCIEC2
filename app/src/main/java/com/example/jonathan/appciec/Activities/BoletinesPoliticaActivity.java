package com.example.jonathan.appciec.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.jonathan.appciec.Adapters.BoletinAdapter;
import com.example.jonathan.appciec.Models.Boletin;
import com.example.jonathan.appciec.R;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

        import java.util.ArrayList;

public class BoletinesPoliticaActivity extends AppCompatActivity {

    private ArrayList<Boletin> lista_boletines = new ArrayList<>();
    private BoletinAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletin_politica);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_Boletines);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        lista_boletines = new ArrayList<>();
        initializeData();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new BoletinAdapter(this, lista_boletines);


        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    private void initializeData( ) {
        lista_boletines.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ArrayList<Boletin> n = new ArrayList<>();
        String url = "http://www.ciec.espol.edu.ec/rest/node/34";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String html = response.getJSONObject("body").getJSONArray("und").getJSONObject(0).getString("safe_value");
                    String h1,h2;
                    if (html.startsWith("<!--")){

                        h1 = html.replace("<!--","");
                        h2=h1.replace("-->"," ");
                    }
                    else{
                        h2=html;
                    }
                    getBoletines(h2);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error","Server Error");
            }


        }

        );
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
    private void getBoletines(String html){
        Document doc = Jsoup.parse(html);
        /*for (Element element : doc.select("*")) {
            if (!element.hasText() && element.isBlock()) {
                element.remove();
            }
        }*/

        Elements boletines = doc.getElementsByTag("tr");
        for (Element elemento : boletines ){
            Elements pdf= elemento.getElementsByTag("a");
            Elements img= elemento.getElementsByTag("img");
            Elements info = elemento.getElementsByTag("pre");
            String URL_BASE = "http://www.ciec.espol.edu.ec";
            String url_pdf = URL_BASE + pdf.first().attr("href");
            String url_img = URL_BASE + img.first().attr("src");
            String informacion = info.first().ownText();

            Boletin bol= new Boletin(informacion,url_pdf,url_img);
            lista_boletines.add(bol);
        }


    }
}
