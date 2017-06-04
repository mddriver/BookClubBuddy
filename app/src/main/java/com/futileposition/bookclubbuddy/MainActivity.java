package com.futileposition.bookclubbuddy;

import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.current_books_recycler_view) RecyclerView mBooksReadingRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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


        mAdapter = new MainBookAdapter(this, myDataset);
        mBooksReadingRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mBooksReadingRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Need to replace this with actions.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
