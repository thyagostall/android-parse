package com.thyago.parseandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject people = new ParseObject("people");
                people.put("firstName", "Sara");
                people.put("lastName", "Smith");
                people.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("people");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ArrayList<String> resultSet = new ArrayList<>(objects.size());
                    for (ParseObject item : objects) {
                        resultSet.add(item.getString("firstName") + " " + item.getString("lastName"));
                    }
                    ListView listViewNames = (ListView) findViewById(R.id.list_view_names);
                    listViewNames.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, resultSet));
                    Log.d(LOG_TAG, "Finished query: " + resultSet);
                } else {
                    Log.d(LOG_TAG, "Error: " + e.getMessage());
                }
            }
        });
    }
}
