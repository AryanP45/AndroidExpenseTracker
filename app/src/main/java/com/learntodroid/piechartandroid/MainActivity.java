package com.learntodroid.piechartandroid;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.learntodroid.piechartandroid.adapter.RecyclerViewAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<rowStructure> expenselist = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Started", "Main Activity");
        //Recycler View Initilization

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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

        while(cursor.moveToNext()) {
            String tempstr = " ";
            //            for showing list of records
        expenselist.add(new rowStructure(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(0)));
            tempstr = " \n " + tempstr + "\t" + cursor.getString(0) + "\t\t\t"
                    + cursor.getString(1) + "\t\t\t" + cursor.getString(2)+
                    "\t\t\t" + cursor.getString(3) +"\t\t\t"+cursor.getString(4)+"\n";

        }
        cursor.close();

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,expenselist);
        recyclerView.setAdapter(recyclerViewAdapter);


        Button b1= (Button) findViewById(R.id.btn1);//add
        Button b4=(Button) findViewById(R.id.btn4);//show All

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),add.class);
                startActivity(i);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),show.class);
                startActivity(i);
            }
        });
    }

    public Float[] calculateExpenses()
    {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_Amount
        };
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

        Float Total=0.0f;
        while(cursor.moveToNext()) {
            Total = Total + Float.parseFloat(cursor.getString(0)) ;
        }
        cursor.close();
        ///////////////////////////////////////////////////////////////////////////////////
        projection = new String[]{ FeedReaderContract.FeedEntry.COLUMN_NAME_Amount};
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_Category + " = ?";
        String[] selectionArgs = { "Entertainment" };


        cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        Float Entertainment=0.0f;
        while(cursor.moveToNext()) {
            Entertainment = Entertainment + Float.parseFloat(cursor.getString(0)) ;
        }
        cursor.close();
///////////////////////////////////////////////////////////////////////////////////
        projection = new String[]{ FeedReaderContract.FeedEntry.COLUMN_NAME_Amount};
        selection = FeedReaderContract.FeedEntry.COLUMN_NAME_Category + " = ?";
        selectionArgs = new String[]{ "Food" };
        sortOrder = BaseColumns._ID + " ASC";
        cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        Float Food=0.0f;
        while(cursor.moveToNext()) {
            Food = Food + Float.parseFloat(cursor.getString(0)) ;
        }
        cursor.close();

        ///////////////////////////////////////////////////////////////////////////////////
        projection = new String[]{ FeedReaderContract.FeedEntry.COLUMN_NAME_Amount};
        selection = FeedReaderContract.FeedEntry.COLUMN_NAME_Category + " = ?";
        selectionArgs = new String[]{ "Study" };
        sortOrder = BaseColumns._ID + " ASC";
        cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        Float Study=0.0f;
        while(cursor.moveToNext()) {
            Study = Study + Float.parseFloat(cursor.getString(0)) ;
        }
        cursor.close();

        ///////////////////////////////////////////////////////////////////////////////////
        projection = new String[]{ FeedReaderContract.FeedEntry.COLUMN_NAME_Amount};
        selection = FeedReaderContract.FeedEntry.COLUMN_NAME_Category + " = ?";
        selectionArgs = new String[]{ "Other" };
        sortOrder = BaseColumns._ID + " ASC";
        cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        Float Other=0.0f;
        while(cursor.moveToNext()) {
            Other = Other + Float.parseFloat(cursor.getString(0)) ;
        }
        cursor.close();

        Entertainment=Entertainment/Total;
        Entertainment*=100;
        String s = String.format("%.2f", Entertainment);
        Entertainment=  Float.valueOf(s);

        Food=Food/Total;
        Food*=100;
        s = String.format("%.2f", Food);
        Food=  Float.valueOf(s);

        Study=Study/Total;
        Study*=100;
        s = String.format("%.2f", Study);
        Study=  Float.valueOf(s);

        Other=Other/Total;
        Other*=100;
        s = String.format("%.2f", Other);
        Other=  Float.valueOf(s);

        Float[] expenses= new Float[4];
        expenses[0]=Entertainment;
        expenses[1]=Food;
        expenses[2]=Study;
        expenses[3]=Other;
        return expenses;
    }

}
