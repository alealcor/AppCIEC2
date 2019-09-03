package com.example.jonathan.appciec.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.jonathan.appciec.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.Point;
import com.google.maps.android.data.geojson.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapasActivity extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;

    public MapasActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mapas, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(-2.146057,  -79.965222))
                        .zoom(15)
                        .bearing(0)
                        .tilt(45)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 9500, null);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener((GoogleMap.OnMyLocationButtonClickListener) mMap.getMyLocation());
                mMap.setOnMyLocationClickListener((GoogleMap.OnMyLocationClickListener) mMap.getMyLocation());
                GeoJsonLayer layer = null;
                try {
                    layer = new GeoJsonLayer(mMap, R.raw.gye_barrios, getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //layer.getDefaultPolygonStyle().setFillColor(getContext().getColor(R.color.colorPrimary));
                //layer.getDefaultPolygonStyle().setStrokeColor(getContext().getColor(R.color.colorPrimary));

                layer.addLayerToMap();
                for(GeoJsonFeature feature : layer.getFeatures()){
                    //Log.d("Stilo", feature.getProperty("nom_barrio").toString());
                    GeoJsonPolygonStyle polygonStyle = new GeoJsonPolygonStyle();
                    if(polygonStyle != null){
                        //Log.d("Menj", "entro");
                        //polygonStyle.setStrokeColor(getContext().getColor(R.color.colorPrimary));
                        //polygonStyle.setFillColor(getContext().getColor(R.color.colorPrimary));
                        //feature.setPolygonStyle(polygonStyle);
                        Geometry point = feature.getGeometry();
                        String example = point.getGeometryObject().toString();
                        JSONObject obj = null;
                        String tmp = "vacios";
                        try {
                            obj = new JSONObject(example);
                           tmp = obj.keys().toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Lista", tmp );

                        //String geometryType = punto.getCenter().toString();
                        /*if(punto != null){
                            LatLng puntero = punto.get(1);
                            mMap.addMarker(new MarkerOptions()
                                    .position(puntero)
                                    .title(feature.getProperty("nom_barrio"))
                                    .snippet(""));
                        }*/
                    }
                    Log.d("Menj", "No entro");
                }
            }
        });


        return rootView;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(),"Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(),"Click me", Toast.LENGTH_LONG).show();
        return false;
    }
}

