package com.example.jonathan.appciec.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jonathan.appciec.Activities.PronosticosActivity;
import com.example.jonathan.appciec.R;

public class PronosticosFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pronosticos, container,false);
        Button button_2018 = view.findViewById(R.id.button_p2018);

        button_2018.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_p2018:
                verPronostico2018();
                break;
        }
    }

        private void verPronostico2018(){
            Intent intent = new Intent(getActivity(), PronosticosActivity.class);
            startActivity(intent);
        }
}
