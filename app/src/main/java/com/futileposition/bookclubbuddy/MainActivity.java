package com.futileposition.bookclubbuddy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.futileposition.bookclubbuddy.SqlBookDatabase.COLUMN_NAME_GOAL_DATE;
import static com.futileposition.bookclubbuddy.SqlBookDatabase.COLUMN_NAME_PAGES;
import static com.futileposition.bookclubbuddy.SqlBookDatabase.COLUMN_NAME_START_DATE;
import static com.futileposition.bookclubbuddy.SqlBookDatabase.COLUMN_NAME_TITLE;
import static com.futileposition.bookclubbuddy.SqlBookDatabase.TABLE_NAME;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.current_books_recycler_view) RecyclerView mBooksReadingRecyclerView;
    private SqlBookDatabase bookDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        File dbTest = new File(String.valueOf(getDatabasePath("BOOK_TABLE")));

        //Create a SQL Database if one doesn't exist

        if (!dbTest.exists()) {
            bookDB = new SqlBookDatabase(this);
        }

        SqlBookDatabase mDbHelper = new SqlBookDatabase(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Create a cursor and get all of the information from the SQLiteDatabase
        Cursor cursor = db.query(
                TABLE_NAME,                     // The table to query
                null,                               // The columns to return, null reads all
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        ArrayList<Book> books = new ArrayList<Book>();
        while(cursor.moveToNext()) {
            Book tempBook = new Book();
            tempBook.setAppIndex(cursor.getInt(0));
            tempBook.setTitle(cursor.getString(1));
            tempBook.setAuthor(cursor.getString(2));
            tempBook.setNumberofPages(cursor.getInt(3));
            DateTime firstDate = new DateTime();
            DateTimeFormatter timeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
            tempBook.setDateStarted(timeFormat.parseDateTime(cursor.getString(4)));
            tempBook.setDateToFinish(timeFormat.parseDateTime(cursor.getString(5)));
            books.add(tempBook);
        }
        cursor.close();
        Book[] myDataset = books.toArray(new Book[books.size()]);

        /* Commenting this out
        //Testing language
        Book book1 = new Book();
        book1.setTitle("Gone With the Wind");
        book1.setAuthor("Margaret Mitchell");
        book1.setNumberofPages(1522);
        DateTime firstDate = new DateTime("2004-12-13T21:39:45.618-08:00");
        DateTime secondDate = new DateTime("2017-12-13T21:39:45.618-08:00");
        book1.setDateStarted(firstDate);
        book1.setDateToFinish(secondDate);
        book1.setPagesRead(0);
        book1.setIsFinished(false);

        Book book2 = new Book();
        book2.setTitle("Hitchhiker's Guide To The Galaxy");
        book2.setAuthor("Douglas Adams");
        book2.setNumberofPages(180);
        firstDate = new DateTime("2016-04-23T21:39:45.618-08:00");
        secondDate = new DateTime("2017-06-14T21:39:45.618-08:00");
        book2.setDateStarted(firstDate);
        book2.setDateToFinish(secondDate);
        book2.setPagesRead(120);
        book2.setIsFinished(false);

        Book book3 = new Book();
        book3.setTitle("A Game Of Thrones");
        book3.setAuthor("George R.R. Martin");
        book3.setNumberofPages(694);
        firstDate = new DateTime("2016-04-23T21:39:45.618-08:00");
        secondDate = new DateTime("2017-06-14T21:39:45.618-08:00");
        book3.setDateStarted(firstDate);
        book3.setDateToFinish(secondDate);
        book3.setPagesRead(694);
        book3.setIsFinished(true);

        Book[] myDataset = new Book[3];
        myDataset[0] = book1;
        myDataset[1] = book2;
        myDataset[2] = book3;
        //End Testing
        */

        mAdapter = new MainBookAdapter(this, myDataset);
        mBooksReadingRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mBooksReadingRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Temporarily set to take me directly to adding a new book
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewBookActivity.class);
                startActivity(intent);
                /*Snackbar.make(view, "Create A New Book", Snackbar.LENGTH_LONG)
                        .setAction("Go", startActivity(intent)).show();
                        */
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
