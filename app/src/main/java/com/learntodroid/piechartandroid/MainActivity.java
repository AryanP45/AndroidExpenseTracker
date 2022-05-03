package com.learntodroid.piechartandroid;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.learntodroid.piechartandroid.FeedReaderDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Float exp[]= calculateExpenses();
        Float val1;
        Float val2;
        Float val3;
        Float val4;
        val1=exp[0];
        val2=exp[1];
        val3=exp[2];
        val4=exp[3];


        TextView textView2= (TextView) findViewById(R.id.txt2);
        textView2.setText(""+ val1+"%");
        textView2.setTypeface(Typeface.SERIF, Typeface.ITALIC);


        TextView textView4= (TextView) findViewById(R.id.txt4);
        textView4.setText(""+ val2+"%");
        textView4.setTypeface(Typeface.SERIF, Typeface.ITALIC);


        TextView textView6= (TextView) findViewById(R.id.txt6);
        textView6.setText(""+val3+"%");
        textView6.setTypeface(Typeface.SERIF, Typeface.ITALIC);


        TextView textView8= (TextView) findViewById(R.id.txt8);
        textView8.setText(""+val4+"%");
        textView8.setTypeface(Typeface.SERIF, Typeface.ITALIC);

        Button b1= (Button) findViewById(R.id.btn1);//add
        Button b2=(Button)findViewById(R.id.btn2);//edit
        Button b3=(Button)findViewById(R.id.btn3);//delete
        Button b4=(Button) findViewById(R.id.btn4);//show All

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),add.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),edit.class);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),delete.class);
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
                projection,             // The array of columns to return (pass null to get all)
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
                projection,             // The array of columns to return (pass null to get all)
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
