package com.example.jonathan.appciec.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jonathan.appciec.Models.Evento;
import com.example.jonathan.appciec.R;

import java.util.ArrayList;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolder>  {

        // Member variables.
        private final ArrayList<Evento> mNoticiaData;
        private final Context mContext;

    public EventoAdapter(ArrayList<Evento> mNoticiaData, Context mContext) {
        this.mNoticiaData = mNoticiaData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
public EventoAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
        inflate(R.layout.item_list_eventos, parent, false));
        }

@Override
public void onBindViewHolder(@NonNull EventoAdapter.ViewHolder holder,
                             int position) {
        // Get current sport.
        Evento currentEvento = mNoticiaData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentEvento);
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
class ViewHolder extends RecyclerView.ViewHolder{

    // Member Variables for the TextViews
    private final ImageView mSportsImage;

    /**
     * Constructor for the ViewHolder, used in onCreateViewHolder().
     *
     * @param itemView The rootview of the list_item.xml layout file.
     */
    ViewHolder(View itemView) {
        super(itemView);

        // Initialize the views
        mSportsImage = itemView.findViewById(R.id.Imagen_Noticia);

        // Set the OnClickListener to the entire view.
//            itemView.setOnClickListener(this);
    }

    void bindTo(Evento currentNoticia){
        // Populate the textviews with data.

        // Load the images into the ImageView using the Glide library.
        Glide.with(mContext).load(
                currentNoticia.getImageResource()).into(mSportsImage);
    }

//        @Override
//        public void onClick(View view) {
//            Noticia currentSport = mNoticiaData.get(getAdapterPosition());
//            Intent detailIntent = new Intent(mContext, DetailActivity.class);
//            detailIntent.putExtra("title", currentSport.getTitle());
//            detailIntent.putExtra("image_resource",
//                    currentSport.getImageResource());
//            mContext.startActivity(detailIntent);
//        }
}
}