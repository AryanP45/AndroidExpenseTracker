package com.learntodroid.piechartandroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class show extends AppCompatActivity {
    private PieChart pieChart;
    private float totalFood=  0;
    private float totalEntertainment=  0;
    private  float totalStudy=0;
    private  float totalOther=0;
    private float totalExpense =0;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);



        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Amount,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Category,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Date
        };

// Filter results WHERE "title" = 'My Title'
      //  String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        //String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " ASC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        TextView textView= (TextView) findViewById(R.id.showDataHere);
        while(cursor.moveToNext()) {
            String tempstr = " ";
            totalExpense= totalExpense + cursor.getFloat(1);
            if (((String) (cursor.getString(2))).equals("Food")){
                totalFood = totalFood +  cursor.getFloat(1);
            }
            if (((String) (cursor.getString(2))).equals("Entertainment")){
                totalEntertainment = totalEntertainment +  cursor.getFloat(1);
            }
            if (((String) (cursor.getString(2))).equals("Study")){
                totalStudy = totalStudy +  cursor.getFloat(1);
            }
            if (((String) (cursor.getString(2))).equals("Other")){
                totalOther = totalOther +  cursor.getFloat(1);
            }
//            for showing list of records

            tempstr = " \n " + tempstr + "\t" + cursor.getString(0) + "\t\t\t"
                    + cursor.getString(1) + "\t\t\t" + cursor.getString(2)+
                    "\t\t\t" + cursor.getString(3) +"\n";
            textView.append(tempstr);
            }
        Log.d("total", ""+totalExpense);

        Log.d("entertainment", "Entertainment: "+totalEntertainment);
        Log.d("food", "Food : "+totalFood);
        Log.d("study", ""+totalStudy);
        Log.d("other", ""+totalOther);

        cursor.close();
        pieChart = findViewById(R.id.activity_main_piechart);
        setupPieChart();
        loadPieChartData();
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

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        float foodpercentage = (totalFood/totalExpense);
        Log.d("food", ""+foodpercentage);
        float entertainmentpercentage = (totalEntertainment/totalExpense);
        Log.d("entertainment", ""+entertainmentpercentage);
        float studypercentage = totalStudy/totalExpense;
        Log.d("study", ""+studypercentage);
        float otherpercentage = totalOther/totalExpense;
        Log.d("other", ""+otherpercentage);

        if(foodpercentage>0) {
            entries.add(new PieEntry(foodpercentage, "Food"));
        }
        if (studypercentage>0) {
            entries.add(new PieEntry(studypercentage, "Study"));
        }
        if (entertainmentpercentage>0) {
            entries.add(new PieEntry(entertainmentpercentage, "Entertainment"));
        }
        if (otherpercentage>0) {
            entries.add(new PieEntry(otherpercentage, "Other"));
        }
//        entries.add(new PieEntry(0.3f, "Housing"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
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
