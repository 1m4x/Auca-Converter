package com.converter.max.converter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Currency extends AppCompatActivity {
    public List<Double> bases;
    public String req_result;
    public JSONObject exchangeRates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
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

        new DownloadWebpageTask().execute("http://api.fixer.io/latest?base=USD");

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner3);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner4);

        List<String> Rates =  new ArrayList<String>();
        Rates.add("USD");
        Rates.add("EUR");
        Rates.add("GBP");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Rates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner3);
        sItems.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Rates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner4);
        sItems2.setAdapter(adapter2);
    }
    private class DownloadWebpageTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL("http://api.fixer.io/latest?base=USD");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();
                // Convert the InputStream into a string
                //String contentAsString = readIt(is, len);

                String contentAsString = readIt(is, len);

                req_result = new String(contentAsString);
                exchangeRates = new JSONObject(req_result);
                return contentAsString;


                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                whenJSONgot();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
    public void Convert(View view) throws JSONException {
        Spinner unit1 = (Spinner) findViewById(R.id.spinner3);
        Spinner unit2 = (Spinner) findViewById(R.id.spinner4);

        EditText Value1 = (EditText) findViewById(R.id.editText3);
        EditText Value2 = (EditText) findViewById(R.id.editText4);

        double a = Double.parseDouble(Value1.getText().toString());
        double b = 0.0;

        if (unit1.getSelectedItem().toString().equals("USD"))
        {
            a = a / bases.get(0);
        } else
        if (unit1.getSelectedItem().toString().equals("EUR"))
        {
            a = a / bases.get(1);
        } else
        if (unit1.getSelectedItem().toString().equals("GBP"))
        {
            a = a / bases.get(2);
        }

        if (unit2.getSelectedItem().toString().equals("USD"))
        {
            b = a * bases.get(0);
        } else
        if (unit2.getSelectedItem().toString().equals("EUR"))
        {
            b = a * bases.get(1);
        } else
        if (unit2.getSelectedItem().toString().equals("GBP"))
        {
            b = a * bases.get(2);
        }
        Value2.setText("" + b);
    }
    public void whenJSONgot() throws JSONException// fill values as soon as we got response from fixer
    {
        bases = new ArrayList<>(); // usd to <sth>
        exchangeRates = exchangeRates.getJSONObject("rates");
        bases.add(1.0); // USD to USD
        bases.add(exchangeRates.getDouble("EUR"));
        bases.add(exchangeRates.getDouble("GBP"));
    }
}

