package com.converter.max.converter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Length extends AppCompatActivity {
    public List<Double> bases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        restoreText();
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        List<String> Lengths =  new ArrayList<String>();
        Lengths.add("kilometers");
        Lengths.add("meters");
        Lengths.add("inches");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Lengths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Lengths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner2);
        sItems2.setAdapter(adapter2);


    }
    public void Convert(View view)
    {
        bases = new ArrayList<>(); // m to <sth>
        bases.add(1000.0); // -> km
        bases.add(1.0); // -> meter
        bases.add(0.0254); // -> inch

        Spinner unit1 = (Spinner) findViewById(R.id.spinner);
        Spinner unit2 = (Spinner) findViewById(R.id.spinner2);

        EditText Value1 = (EditText) findViewById(R.id.editText);
        EditText Value2 = (EditText) findViewById(R.id.editText2);

        //double a = Integer.parseInt(Value1.getText().toString() );
        //double b = Integer.parseInt(Value2.getText().toString());

        double a = Double.parseDouble(Value1.getText().toString());
        double b = 0.0;

        if (unit1.getSelectedItem().toString().equals("kilometers"))
        {
            a = a * bases.get(0); // km to meter
        } else
        if (unit1.getSelectedItem().toString().equals("meters"))
        {
            a = a * bases.get(1); // meter to meter
        } else
        if (unit1.getSelectedItem().toString().equals("inches"))
        {
            a = a * bases.get(2); // km to meter
        }

        if (unit2.getSelectedItem().toString().equals("kilometers"))
        {
            b = a / bases.get(0);
        } else
        if (unit2.getSelectedItem().toString().equals("meters"))
        {
            b = a / bases.get(1);
        } else
        if (unit2.getSelectedItem().toString().equals("inches"))
        {
            b = a / bases.get(2);
        }

        Value2.setText("" + b);
    }
    @Override
    protected void onDestroy()
    {
        EditText Value1 = (EditText) findViewById(R.id.editText);
        EditText Value2 = (EditText) findViewById(R.id.editText2);

        SharedPreferences.Editor prefs = getPreferences(MODE_PRIVATE).edit();
        prefs.putString("saved", "1");
        prefs.putString("Length-1", Value1.getText().toString());
        prefs.putString("Length-2", Value2.getText().toString());
        prefs.apply();
        super.onDestroy();
    }
    public void restoreText()
    {
        EditText Value1 = (EditText) findViewById(R.id.editText);
        EditText Value2 = (EditText) findViewById(R.id.editText2);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String restoredText = prefs.getString("saved", null);
        if (restoredText != null)
        {
            Value1.setText(prefs.getString("Length-1", "0"));
            Value2.setText(prefs.getString("Length-2", "0"));
        }
    }
}
