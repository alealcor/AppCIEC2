package com.example.jonathan.appciec;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class BoletinesFragment extends Fragment implements View.OnClickListener {
    Button button_politica;
    Button button_otros;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boletines, container,false);
        button_politica = (Button) view.findViewById(R.id.button_politica);
        button_otros =  (Button) view.findViewById((R.id.button_otros));
        button_politica.setOnClickListener(this);
        button_otros.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_politica:
                verBoletines(v);
                break;
            case R.id.button_otros:
                verBoletines(v);
                break;
        }
    }

    public void verBoletines(View view){
        Intent intent = new Intent(getActivity(), BoletinesPoliticaActivity.class);
        startActivity(intent);
    }

    public void verArticulosDivulgacion(View view){
        Intent intent = new Intent(getActivity(), BoletinesPoliticaActivity.class);
        startActivity(intent);

    }


}
