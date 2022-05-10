package com.learntodroid.piechartandroid;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class show extends AppCompatActivity {
    private PieChart pieChart;
    private BarChart barChart;
    private float totalFood = 0;
    private float totalEntertainment = 0;
    private float totalStudy = 0;
    private float totalOther = 0;
    private float totalExpense = 0;
    private float totalexpenseofDate = 0;

    private Button piebtn;
    private Button barbtn;
    private PieChart pie;
    private BarChart bar;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Log.d("Activity Started", "I'm here");
        piebtn = findViewById(R.id.pieChart);
        barbtn = findViewById(R.id.barChart);
        pie = findViewById(R.id.show_piechart);
        bar = findViewById(R.id.show_barchart);
        mDisplayDate = findViewById(R.id.tvDate1);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(show.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
                if (pie.getVisibility()==View.VISIBLE){
                    getdata();
                    loadPieChartData();

                }
                if (bar.getVisibility()==View.VISIBLE){
                    getdata();
                    setUpBarChart();
                }
            }
        };

        piebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pie.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
                loadPieChartData();
            }
        });

        barbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pie.setVisibility(View.GONE);
                bar.setVisibility(View.VISIBLE);
                barChart = findViewById(R.id.show_barchart);
                setUpBarChart();
            }
        });


        pieChart = findViewById(R.id.show_piechart);
        setupPieChart();
        loadPieChartData();

    }
    public void getdata(){
        //starting database operations
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " ASC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        String date = mDisplayDate.getText().toString();
        int i=0;
        int countfood=0,countstudy=0,countentertainment=0,countother=0;
        while (cursor.moveToNext()) {
            totalExpense = totalExpense + cursor.getFloat(1);
            Log.d("date", "onCreate: "+cursor.getString(3));

            switch (((String) (cursor.getString(2)))) {
                case "Food": {
                    Log.d("food", "IN FOOD"+((String) (cursor.getString(3))));
                    if (((String) (cursor.getString(3))).equals(date)) {
                        Log.d("food", "inside Food");
                        totalFood = totalFood + cursor.getFloat(1);
                        totalexpenseofDate += cursor.getFloat(1);
                        countfood++;
                    }
                    if (((String) (cursor.getString(2))).equals("Select Date")) {
                        totalFood = totalFood + cursor.getFloat(1);
                    }
                }

                break;
                case "Entertainment":
                    if (((String) (cursor.getString(3))).equals(date)) {
                        totalEntertainment = totalEntertainment + cursor.getFloat(1);
                        totalexpenseofDate += cursor.getFloat(1);
                        countentertainment++;
                    }
                    if (((String) (cursor.getString(2))).equals("Select Date")) {
                        totalEntertainment = totalEntertainment + cursor.getFloat(1);
                    }

                    break;
                case "Study":
                    if (((String) (cursor.getString(3))).equals(date)) {
                        totalStudy = totalStudy + cursor.getFloat(1);
                        totalexpenseofDate += cursor.getFloat(1);
                        countstudy++;
                    }
                    if (((String) (cursor.getString(2))).equals("Select Date")) {
                        totalStudy = totalStudy + cursor.getFloat(1);
                    }

                    break;
                case "Other":
                    if (((String) (cursor.getString(3))).equals(date)) {
                        totalOther = totalOther + cursor.getFloat(1);
                        totalexpenseofDate += cursor.getFloat(1);
                        countother++;
                    }
                    if (((String) (cursor.getString(2))).equals("Select Date")) {
                        totalOther = totalOther + cursor.getFloat(1);
                    }
                    break;
            }
        }
        cursor.close();
        if (countfood<=0 && countentertainment<=0 && countstudy<=0 && countother<=0){
            totalFood=0;
            totalExpense=0;
            totalexpenseofDate=0;
            totalEntertainment=0;
            totalOther=0;
            totalStudy=0;
        }
        if (countfood<=0){
            totalFood=0;
        }
        if (countentertainment<=0){
            totalEntertainment=0;
        }
        if (countstudy<=0){
            totalStudy=0;
        }
        if (countother<=0){
            totalOther=0;
        }

        Log.d("total", "onCreate: "+totalFood);
        Log.d("total", "onCreate: "+totalEntertainment);
        Log.d("total", "onCreate: "+totalStudy);
        Log.d("total", "onCreate: "+totalOther);
        Log.d("total", "onCreate: "+totalexpenseofDate);
        Log.d("total", "onCreate: "+totalExpense);
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Total Expenses");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void setUpBarChart() {

        ArrayList<BarEntry> entries = new ArrayList<>();
        if (totalFood > 0) {
            entries.add(new BarEntry((float) 1.2, totalFood));
        }
        if (totalStudy > 0) {
            entries.add(new BarEntry((float) 2.2, totalStudy));
        }
        if (totalEntertainment > 0) {
            entries.add(new BarEntry((float) 3.3, totalEntertainment));
        }
        if (totalOther > 0) {
            entries.add(new BarEntry((float) 4.2, totalOther));
        }
        BarDataSet barDataSet = new BarDataSet(entries, "Category");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("");
        if (totalFood>0){
            xAxisLabel.add("Food");
        }else {
            xAxisLabel.add("");
        }

        if (totalStudy>0){
            xAxisLabel.add("Study");
        }else{
            xAxisLabel.add("");
        }

        if (totalEntertainment>0){
            xAxisLabel.add("Entertainment");
        }else {
            xAxisLabel.add("");
        }

        if (totalOther>0){
            xAxisLabel.add("Other");
        }else {
            xAxisLabel.add("");
        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int) value);
            }
        };

        xAxis.setGranularity(1.1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.getDescription().setText("");
        barChart.animateX(1400, Easing.EaseInElastic);

    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        Log.d("textdate", "loadPieChartData: "+mDisplayDate.getText().toString().equals("Select Date"));
        if (mDisplayDate.getText().toString().equals("Select Date")) {
            float foodpercentage = (totalFood / totalExpense);
            Log.d("food", "" + foodpercentage);
            float entertainmentpercentage = (totalEntertainment / totalExpense);
            Log.d("entertainment", "" + entertainmentpercentage);
            float studypercentage = totalStudy / totalExpense;
            Log.d("study", "" + studypercentage);
            float otherpercentage = totalOther / totalExpense;
            Log.d("other", "" + otherpercentage);

            if (foodpercentage > 0) {
                entries.add(new PieEntry(foodpercentage, "Food"));
            }
            if (studypercentage > 0) {
                entries.add(new PieEntry(studypercentage, "Study"));
            }
            if (entertainmentpercentage > 0) {
                entries.add(new PieEntry(entertainmentpercentage, "Entertainment"));
            }
            if (otherpercentage > 0) {
                entries.add(new PieEntry(otherpercentage, "Other"));
            }
        } else {
            Log.d("textdate", "loadPieChartData: true");
            float foodpercentage = (totalFood / totalexpenseofDate);
            Log.d("food", "" + foodpercentage);
            float entertainmentpercentage = (totalEntertainment / totalexpenseofDate);
            Log.d("entertainment", "" + entertainmentpercentage);
            float studypercentage = totalStudy / totalexpenseofDate;
            Log.d("study", "" + studypercentage);
            float otherpercentage = totalOther / totalexpenseofDate;
            Log.d("other", "" + otherpercentage);

            if (foodpercentage > 0) {
                entries.add(new PieEntry(foodpercentage, "Food"));
            }
            if (studypercentage > 0) {
                entries.add(new PieEntry(studypercentage, "Study"));
            }
            if (entertainmentpercentage > 0) {
                entries.add(new PieEntry(entertainmentpercentage, "Entertainment"));
            }
            if (otherpercentage > 0) {
                entries.add(new PieEntry(otherpercentage, "Other"));
            }
        }


        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}
