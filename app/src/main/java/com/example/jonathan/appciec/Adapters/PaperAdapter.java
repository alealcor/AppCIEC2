package com.example.jonathan.appciec.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jonathan.appciec.Activities.Info_InvestigacionesActivity;
import com.example.jonathan.appciec.Models.FirebaseConnector;
import com.example.jonathan.appciec.Models.Paper;
import com.example.jonathan.appciec.Models.SessionHandler;
import com.example.jonathan.appciec.R;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.ViewHolder> implements Filterable {
    // Member variables.
    private final ArrayList<Paper> mPapersData;
    private final ArrayList<Paper> mPapersComplete;
    private Map favoritos;
    private final Context mContext;
    private FirebaseConnector fconnector;
    private SessionHandler shandler;

    public PaperAdapter(Context context, ArrayList<Paper> mPapersData, ArrayList<Paper> copy, Map favoritos, FirebaseConnector reff, SessionHandler shandler) {
        this.mPapersData = mPapersData;
        this.mPapersComplete = copy;
        this.mContext = context;
        this.favoritos = favoritos;
        this.fconnector = reff;
        this.shandler = shandler;
    }

    @NonNull
    @Override
    public PaperAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_list_investigaciones, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaperAdapter.ViewHolder holder,
                                 int position) {
        // Get current Paper.
        Paper currentPaper = mPapersData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentPaper);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mPapersData.size();
    }

    static public boolean validateTitleSearchQuery(String query){

        if(query==null)
        {
            return false;
        }
        boolean valido = true;

        boolean queryCorrecto = query.matches("[a-zñÑA-ZÀ-ÿ0-9 ]+");

        if(!queryCorrecto) {
            valido = false;
        }else if(query.length()>50) {
            valido = false;
        }

        if (Objects.equals(query, "")){
            valido = true;
        }

        return valido;
    }

    @Override
    public Filter getFilter() {
        return papersFilter;
    }

    private final Filter papersFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Paper> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                filteredList.addAll(mPapersComplete);

            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                String cleanFilterPattern = filterPattern.replaceAll( "[^a-zA-Z0-9 ]+" , "" );
                if(validateTitleSearchQuery(cleanFilterPattern)) {
                    for (Paper item : mPapersComplete) {
                        if (item.getTitulo().toLowerCase().contains(filterPattern)) {

                            filteredList.add(item);
                        }
//                        else{
//                            TextView message = findViewById(R.id.searchMessage);
//                       }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPapersData.clear();
            mPapersData.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Member Variables for the TextViews
        private final TextView mTitleText;
        private final TextView mAutorText;
        private final ImageView fav;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item_Investigacion_Investigacion.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);
            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mAutorText = itemView.findViewById(R.id.subTitle);

            if (shandler.estaIniciado()) {
                fav = itemView.findViewById(R.id.fav_imagen);
            }
            else{
                fav = null;
            }

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(final Paper paper){
            // Populate the textviews with data.
            mTitleText.setText(paper.getTitulo());
            mAutorText.setText(paper.getAutores());
            if (shandler.estaIniciado()) {
                if(favoritos.containsKey(paper.getTitulo())){
                    fav.setImageResource(R.drawable.ic_star_black_24dp);
                }else{
                    fav.setImageResource(R.drawable.ic_star_border_black_24dp);
                }
                MyClickListener favList = new MyClickListener(paper);
                fav.setOnClickListener(favList);
            }
        }

        public class MyClickListener implements View.OnClickListener {
            private Paper paper;

            MyClickListener(Paper paper){
                this.paper = paper;
            }
            public void onClick(View v){
                if(favoritos.containsKey(paper.getTitulo())){
                    fconnector.eliminarFavorito(paper,shandler,favoritos);
                    fav.setImageResource(R.drawable.ic_star_border_black_24dp);
                }else{
                    fconnector.agregarFavorito(paper,shandler,favoritos);
                    fav.setImageResource(R.drawable.ic_star_black_24dp);
                }
            }

        }
        @Override
        public void onClick(View view) {
            Paper currentPaper = mPapersData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, Info_InvestigacionesActivity.class);
            detailIntent.putExtra("title", currentPaper.getTitulo());
            detailIntent.putExtra("link", currentPaper.getarticleLink());
            detailIntent.putExtra("autores_paper", currentPaper.getAutores());
            detailIntent.putExtra("fecha_paper", currentPaper.getFecha());
            detailIntent.putExtra("pais_paper", currentPaper.getPais());
            detailIntent.putExtra("journal_paper", currentPaper.getJournal());
            detailIntent.putExtra("overview_link", currentPaper.getLink_journalOverview());
            mContext.startActivity(detailIntent);
        }
    }
}
