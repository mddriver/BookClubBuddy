package com.futileposition.bookclubbuddy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.edit;
import static com.futileposition.bookclubbuddy.SqlBookDatabase.TABLE_NAME;

public class NewBookActivity extends AppCompatActivity {

    @BindView(R.id.submitNewBook) Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Temporarily set to take me back to main activity.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewBookActivity.this, MainActivity.class);
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText authorText = (EditText)findViewById(R.id.bookAuthorText);
                String author = authorText.getText().toString();
                EditText titleText = (EditText)findViewById(R.id.bookTitleText);
                String title = titleText.getText().toString();
                EditText pagesText = (EditText)findViewById(R.id.bookTotalPagesText);
                int pages = Integer.parseInt(pagesText.getText().toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd");
                Date date = new Date();
                String start_date = dateFormat.format(date);
                DatePicker goalDatePicker = (DatePicker)findViewById(R.id.goalDatePicker);
                Date goalAsDate = new Date(goalDatePicker.getYear() - 1900, goalDatePicker.getMonth(), goalDatePicker.getDayOfMonth());
                String end_date = dateFormat.format(goalAsDate);

                //Create a new SQL Entry with Title, Author, Pages, and Date Finished.
                SqlBookDatabase.createBook(getApplicationContext(), title, author, pages, start_date, end_date);

            }
        });
    }

}
