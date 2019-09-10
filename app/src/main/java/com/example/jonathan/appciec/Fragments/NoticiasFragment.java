package com.example.jonathan.appciec.Fragments;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jonathan.appciec.Adapters.NoticiaAdapter;
import com.example.jonathan.appciec.Models.Noticia;
import com.example.jonathan.appciec.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticiasFragment extends Fragment {

    private ArrayList<Noticia> mNoticiasData;
    private NoticiaAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView_Noticias);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mNoticiasData = new ArrayList<>();
        mAdapter = new NoticiaAdapter(getActivity(), mNoticiasData);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
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
            Log.d("TAG", "onPostExecute: " + result);

            mAdapter.notifyDataSetChanged();


        }

    }

    private void initializeData() {
        Document doc = null;


        String mainurl = "http://www.ciec.espol.edu.ec";
        String url = "http://www.ciec.espol.edu.ec/noticias";

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            Log.e("tag", e.getMessage(), e);
        }
        Elements articles = doc.select("article");


        for (Element article : articles) {
            String title = article.select("h2").text();
            String imgLink = mainurl + article.select("img").attr("src");
            String articleLink = mainurl + article.select("a").attr("href");
            Document noticiaDoc = null;
            try {
                noticiaDoc = Jsoup.connect(articleLink).get();
            } catch (IOException e) {
            }
            String content = noticiaDoc.select("article").text();

            Noticia noticia = new Noticia(title, content, imgLink);
            mNoticiasData.add(noticia);
        }
        Elements nextPage = doc.select("#block-system-main > div > div.text-center > ul > li.next > a");

        while (nextPage.size() == 1) {
            String newUrl = mainurl + nextPage.attr("href");
            try {


                doc = Jsoup.connect(newUrl).get();


            } catch (IOException e) {
            }
            articles = doc.select("article");
            for (Element article : articles) {
                //                Document noticiaDoc = null;
                String title = article.select("h2").text();
                String imgLink = mainurl + article.select("img").attr("src");
                String articleLink = mainurl + article.select("a").attr("href");
                Document noticiaDoc = null;
                try {
                    noticiaDoc = Jsoup.connect(articleLink).get();
                } catch (IOException e) {
                }
                String content = noticiaDoc.select("article").text();


                Noticia noticia = new Noticia(title, content, imgLink);
                mNoticiasData.add(noticia);
                Log.d("title", title);
            }
            nextPage = doc.select("#block-system-main > div > div.text-center > ul > li.next > a");
            Log.d("tag", String.valueOf(mNoticiasData.size()));



        }


    }

}
