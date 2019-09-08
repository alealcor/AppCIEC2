package com.example.jonathan.appciec.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
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
import com.google.gson.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
                    layer = new GeoJsonLayer(mMap,R.raw.gye_barrios,getContext());
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
                        double valor = Double.parseDouble(feature.getProperty("pobbarr"));
                        if(valor < 18){
                            polygonStyle.setStrokeColor(getContext().getColor(R.color.colorMap8));
                            polygonStyle.setFillColor(getContext().getColor(R.color.colorMap8));
                            feature.setPolygonStyle(polygonStyle);
                        }else if (valor >= 18 && valor < 224){
                            polygonStyle.setStrokeColor(getContext().getColor(R.color.colorMap7));
                            polygonStyle.setFillColor(getContext().getColor(R.color.colorMap7));
                            feature.setPolygonStyle(polygonStyle);
                        }else if (valor >= 224 && valor < 411){
                            polygonStyle.setStrokeColor(getContext().getColor(R.color.colorMap6));
                            polygonStyle.setFillColor(getContext().getColor(R.color.colorMap6));
                            feature.setPolygonStyle(polygonStyle);
                        }else if (valor >= 411 && valor < 514){
                            polygonStyle.setStrokeColor(getContext().getColor(R.color.colorMap5));
                            polygonStyle.setFillColor(getContext().getColor(R.color.colorMap5));
                            feature.setPolygonStyle(polygonStyle);
                        }else if (valor >= 514 && valor < 685){
                            polygonStyle.setStrokeColor(getContext().getColor(R.color.colorMap4));
                            polygonStyle.setFillColor(getContext().getColor(R.color.colorMap4));
                            feature.setPolygonStyle(polygonStyle);
                        }else if (valor >= 685 && valor < 1337){
                            polygonStyle.setStrokeColor(getContext().getColor(R.color.colorMap3));
                            polygonStyle.setFillColor(getContext().getColor(R.color.colorMap3));
                            feature.setPolygonStyle(polygonStyle);
                        }else if (valor >= 1337 && valor < 2873){
                            polygonStyle.setStrokeColor(getContext().getColor(R.color.colorMap2));
                            polygonStyle.setFillColor(getContext().getColor(R.color.colorMap2));
                            feature.setPolygonStyle(polygonStyle);
                        }else{
                            polygonStyle.setStrokeColor(getContext().getColor(R.color.colorMap1));
                            polygonStyle.setFillColor(getContext().getColor(R.color.colorMap1));
                            feature.setPolygonStyle(polygonStyle);
                        }

                        List<Geometry> geometryList = (List<Geometry>) feature.getGeometry().getGeometryObject();
                        ArrayList<GeoJsonPolygon> geoJsonPolygon = new ArrayList<GeoJsonPolygon>();
                        for (Geometry geometry : geometryList) {
                            GeoJsonPolygon polygon = (GeoJsonPolygon) geometry;
                            geoJsonPolygon.add(polygon);
                        }
                        ArrayList<LatLng> puntos = new ArrayList<LatLng>();
                        for(GeoJsonPolygon polygon : geoJsonPolygon){
                             puntos =  polygon.getOuterBoundaryCoordinates();
                        }
                        double[] centroid = { 0.0, 0.0 };
                        for (int i = 0; i < puntos.size(); i++) {
                            centroid[0] += puntos.get(i).latitude;
                            centroid[1] += puntos.get(i).longitude;
                        }
                        int totalPoints = puntos.size();
                        centroid[0] = centroid[0] / totalPoints;
                        centroid[1] = centroid[1] / totalPoints;

                        LatLng centroide = new LatLng(centroid[0], centroid[1]);

                        if(centroide != null){
                            mMap.addMarker(new MarkerOptions()
                                    .position(centroide)
                                    .title(feature.getProperty("nom_barrio"))
                                    .snippet("Población: "+ feature.getProperty("pobbarr")+ " personas"));
                        }
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

