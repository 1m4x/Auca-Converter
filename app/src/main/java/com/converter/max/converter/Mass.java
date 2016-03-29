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

public class Mass extends AppCompatActivity {
    public List<Double> bases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);
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
        Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);

        List<String> MassUnits =  new ArrayList<String>();
        MassUnits.add("g");
        MassUnits.add("kg");
        MassUnits.add("lbs");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, MassUnits);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner5);
        sItems.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, MassUnits);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner6);
        sItems2.setAdapter(adapter2);
    }

    public void Convert(View view)
    {
        bases = new ArrayList<>(); // g to <sth>
        bases.add(1.0); // -> g
        bases.add(1000.0); // -> kg
        bases.add(453.592); // -> lbs

        Spinner unit1 = (Spinner) findViewById(R.id.spinner5);
        Spinner unit2 = (Spinner) findViewById(R.id.spinner6);

        EditText Value1 = (EditText) findViewById(R.id.editText5);
        EditText Value2 = (EditText) findViewById(R.id.editText6);

        //double a = Integer.parseInt(Value1.getText().toString() );
        //double b = Integer.parseInt(Value2.getText().toString());

        double a = Double.parseDouble(Value1.getText().toString());
        double b = 0.0;

        if (unit1.getSelectedItem().toString().equals("g"))
        {
            a = a * bases.get(0); // g to g
        } else
        if (unit1.getSelectedItem().toString().equals("kg"))
        {
            a = a * bases.get(1); // kg to g
        } else
        if (unit1.getSelectedItem().toString().equals("lbs"))
        {
            a = a * bases.get(2); // lbs to g
        }

        if (unit2.getSelectedItem().toString().equals("g"))
        {
            b = a / bases.get(0);
        } else
        if (unit2.getSelectedItem().toString().equals("kg"))
        {
            b = a / bases.get(1);
        } else
        if (unit2.getSelectedItem().toString().equals("lbs"))
        {
            b = a / bases.get(2);
        }

        Value2.setText("" + b);
    }
    @Override
    protected void onDestroy()
    {
        EditText Value1 = (EditText) findViewById(R.id.editText5);
        EditText Value2 = (EditText) findViewById(R.id.editText6);

        SharedPreferences.Editor prefs = getPreferences(MODE_PRIVATE).edit();
        prefs.putString("saved", "1");
        prefs.putString("Mass-1", Value1.getText().toString());
        prefs.putString("Mass-2", Value2.getText().toString());
        prefs.apply();
        super.onDestroy();
    }
    public void restoreText()
    {
        EditText Value1 = (EditText) findViewById(R.id.editText5);
        EditText Value2 = (EditText) findViewById(R.id.editText6);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String restoredText = prefs.getString("saved", null);
        if (restoredText != null)
        {
            Value1.setText(prefs.getString("Mass-1", "0"));
            Value2.setText(prefs.getString("Mass-2", "0"));
        }
    }
}
