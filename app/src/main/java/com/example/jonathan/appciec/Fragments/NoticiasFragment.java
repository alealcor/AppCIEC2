package com.example.jonathan.appciec.Fragments;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jonathan.appciec.Models.Noticia;
import com.example.jonathan.appciec.Adapters.NoticiaAdapter;
import com.example.jonathan.appciec.R;

import java.util.ArrayList;

public class NoticiasFragment extends Fragment {

    private ArrayList<Noticia> mNoticiasData;
    private NoticiaAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.recyclerView_Noticias);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mNoticiasData = new ArrayList<>();
        mAdapter = new NoticiaAdapter(getActivity(), this.mNoticiasData);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the RecyclerView.
        // Member variables.
        RecyclerView mRecyclerView = getView().findViewById(R.id.recyclerView_Noticias);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the ArrayList that will contain the data.
        this.mNoticiasData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new NoticiaAdapter(getActivity(), this.mNoticiasData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        initializeData();
    }

//    private void initializeData() {
//        // Clear the existing data (to avoid duplication).
//        mNoticiasData.clear();
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        String url = "http://www.ciec.espol.edu.ec/rest/node/20";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String html = response.getJSONObject("body").getJSONArray("und").getJSONObject(0).getString("safe_value");
//                    mNoticiasData(html);
//                    mAdapter.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.e("Error","Server Error");
//            }
//
//
//        });
//
//        requestQueue.add(jsonObjectRequest);
//
//    }

    private void initializeData() {
        // Get the resources from the XML file.
        String[] NoticiasList = getResources()
                .getStringArray(R.array.titulos_noticias);
        String[] NoticiasInfo = getResources()
                .getStringArray(R.array.contenido_noticias);
        TypedArray NoticiasImageResources = getResources()
                .obtainTypedArray(R.array.imagenes_noticias);

        // Clear the existing data (to avoid duplication).
       this.mNoticiasData.clear();

        // Create the ArrayList of Noticia objects with the titles and
        // information about each Noticia
        for (int i = 0; i < NoticiasList.length; i++) {
            mNoticiasData.add(new Noticia(NoticiasList[i], NoticiasInfo[i],
                    NoticiasImageResources.getResourceId(i, 0)));
//            Log.d("SAVING", "initializeData: " + NoticiasInfo[i]);
        }

        // Recycle the typed array.
        NoticiasImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }
}
