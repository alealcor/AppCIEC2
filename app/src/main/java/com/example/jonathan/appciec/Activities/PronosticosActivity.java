package com.example.jonathan.appciec.Activities;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.jonathan.appciec.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.opencsv.CSVReader;

import java.io.*;


import java.util.ArrayList;
import java.util.List;

public class PronosticosActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private LineChart chart;
    private SeekBar seekBarX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pronosticos);

        setTitle("LineChartActivity1");



        seekBarX = findViewById(R.id.seekBar1);
        seekBarX.setMax(54);
        seekBarX.setOnSeekBarChangeListener(this);
//



        {   // // Chart Style // //
            chart = findViewById(R.id.chart1);

            // background color
            chart.setBackgroundColor(Color.WHITE);

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }




        // add data

        setData();
        seekBarX.setProgress(seekBarX.getMax());

        // draw points over time
        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
    }

    private void setData() {

        final ArrayList<String> x_axis_data = new ArrayList<>();
        ArrayList<Entry> pib_values = new ArrayList<>();

        ArrayList<Entry> consumo_values = new ArrayList<>();
        ArrayList<Entry> inversion_values = new ArrayList<>();

        CSVReader reader = null;
        AssetManager am = getApplicationContext().getAssets();

        try {
            InputStream is = am.open("info_tabla_pagina.csv");
            reader = new CSVReader(new InputStreamReader(is), ';', '"', 0);
            List<String[]> myEntries = reader.readAll();
            int n = 0;
            for(String[] entry: myEntries){
                x_axis_data.add(entry[0]);
                pib_values.add(new Entry(n, Float.parseFloat(entry[1])));
                consumo_values.add(new Entry(n, Float.parseFloat(entry[2])));
                inversion_values.add(new Entry(n, Float.parseFloat(entry[3])));
                n++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return x_axis_data.get((int)value);
            }

        });


        LineDataSet set1;
        LineDataSet set2;
        LineDataSet set3;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(pib_values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(pib_values, "PIB");
            set2 = new LineDataSet(inversion_values, "Inversión");
            set3 = new LineDataSet(consumo_values, "Consumo");

            set1.setDrawIcons(false);

            // black lines and points
            set1.setColor(Color.BLUE);
            set1.setCircleColor(Color.BLUE);

            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);

            // line thickness and point size
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);



            // text size of values
            set1.setValueTextSize(9f);


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            dataSets.add(set2);
            dataSets.add(set3);

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//        tvX.setText(String.valueOf(100f/seekBarX.getProgress()));
//        tvY.setText(String.valueOf(seekBarY.getProgress()));
        chart.setVisibleXRangeMaximum(seekBarX.getProgress()+3);
        chart.setVisibleXRangeMinimum(seekBarX.getProgress()+3);
        chart.moveViewToX(57-seekBarX.getProgress()+3);

        // redraw
        chart.invalidate();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
