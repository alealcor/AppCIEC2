package com.example.jonathan.appciec.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jonathan.appciec.Models.Noticia;
import com.example.jonathan.appciec.Activities.NoticiaActivity;
import com.example.jonathan.appciec.R;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the sports data.
 */
public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.ViewHolder>  {

    // Member variables.
    private final ArrayList<Noticia> mNoticiaData;
    private final Context mContext;

    public NoticiaAdapter(Context context, ArrayList<Noticia> NoticiaData) {
        this.mNoticiaData = NoticiaData;
        this.mContext = context;
    }



    @NonNull
    @Override
    public NoticiaAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_list_noticias, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiaAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
        Noticia currentNoticia = mNoticiaData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentNoticia);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mNoticiaData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        // Member Variables for the TextViews
        private final TextView mTituloText;
        private final ImageView mNoticiaImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTituloText = itemView.findViewById(R.id.titulo_Noticia);
            mNoticiaImage = itemView.findViewById(R.id.Imagen_Noticia);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Noticia currentNoticia){
            // Populate the textviews with data.
            mTituloText.setText(currentNoticia.getTitulo());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentNoticia.getImageResource()).centerCrop().into(mNoticiaImage);
        }

        @Override
        public void onClick(View view) {
            Noticia currentNoticia = mNoticiaData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, NoticiaActivity.class);
            detailIntent.putExtra("titulo_noticia", currentNoticia.getTitulo());
            detailIntent.putExtra("contenido_noticia", currentNoticia.getContenido());
            detailIntent.putExtra("imagen_noticia",
                    currentNoticia.getImageResource());
            mContext.startActivity(detailIntent);
        }
    }
}