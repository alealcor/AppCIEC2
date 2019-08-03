package com.example.jonathan.appciec.Fragments;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.jonathan.appciec.Models.Evento;
import com.example.jonathan.appciec.Adapters.EventoAdapter;
import com.example.jonathan.appciec.R;

public class EventosFragment extends Fragment {
    private ArrayList<Evento> mNoticiasData;
    private EventoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);


        RecyclerView mRecyclerView = view.findViewById(R.id.cafe_ciec);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mNoticiasData = new ArrayList<>();
        mAdapter = new EventoAdapter(this.mNoticiasData, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        initializeData();
        WebView browser = view.findViewById(R.id.visorweb);
        browser.getSettings().setBuiltInZoomControls(true);

        browser.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // Cargamos la web
        browser.loadUrl("http://www.ciec.espol.edu.ec/sites/default/files/imagenes/CAFE-CIEC_WEB-FB.jpg");

        return view;

    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
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
//    }


    private void initializeData() {
        // Get the resources from the XML file.
        TypedArray NoticiasImageResources = getResources()
                .obtainTypedArray(R.array.imagenes_eventos);

        // Clear the existing data (to avoid duplication).
        this.mNoticiasData.clear();

        // Create the ArrayList of Eventos objects with the titles and
        // information about each Eventos
        for (int i = 0; i < NoticiasImageResources.length(); i++) {
            mNoticiasData.add(new Evento(
                    NoticiasImageResources.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        NoticiasImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }



}


