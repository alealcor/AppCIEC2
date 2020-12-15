package com.example.jonathan.appciec.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jonathan.appciec.Models.Boletin;
import com.example.jonathan.appciec.R;
import java.io.IOException;
import java.util.ArrayList;

/**
 * {@link BaseAdapter} para poblar coches en un grid view
 */

public class BoletinAdapter extends RecyclerView.Adapter<BoletinAdapter.ViewHolder> {
    private final ArrayList<Boletin> lista;
    private final Context mContext;


    public BoletinAdapter(Context context, ArrayList<Boletin> listado) {
        this.lista = listado;
        this.mContext = context;
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */

    @NonNull
    @Override
    public BoletinAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return new BoletinAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_grid_boletin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoletinAdapter.ViewHolder holder,
                                 int position) {
        // Get current Paper.
        Boletin currentBoletin = lista.get(position);

        // Populate the textviews with data.
        try {
            holder.bindTo(currentBoletin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return lista.size();

    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Member Variables for the TextViews
        private final TextView titulo;
        private final ImageView url_imagen;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item_Investigacion_Investigacion.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            titulo = itemView.findViewById(R.id.info_boletin);
            url_imagen = itemView.findViewById(R.id.img_boletin);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Boletin boletin) throws IOException {
            // Populate the textviews with data.
            titulo.setText(boletin.getInformacion());
            Log.e("img",boletin.getUrl_img());
            Glide.with(mContext).load(boletin.getUrl_img()).into(url_imagen);
        }

        @Override
        public void onClick(View view) {
            Boletin currentBoletin = lista.get(getAdapterPosition());
            Uri uri = Uri.parse(currentBoletin.getUrl_pdf());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }
    }

}
