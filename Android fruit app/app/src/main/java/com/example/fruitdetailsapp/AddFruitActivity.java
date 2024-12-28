package com.example.fruitdetailsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;


public class AddFruitActivity extends AppCompatActivity {
    final HashMap<String, String> params = new HashMap<>();

    // Creating string object for all fruits, to store all fruits into array
    String[] Fruit;
    ListView FruitList;
    Button buttonAddNewFruit;
    Context c = this;

    // Creating array to store all Fruits
    ArrayList<Fruit> allFruits = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fruit);

        Button buttonAddFruit = (Button) findViewById(R.id.buttonAddFruit);

        final EditText Name = (EditText) findViewById(R.id.editTextFruitName);
        final EditText Price = (EditText) findViewById(R.id.editTextFruitPrice);

        // Creating on click listener for the add student button
        buttonAddNewFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            // creating public void on click
            public void onClick(View view) {
                // Creating gson object
                Gson gson = new Gson();
                // Setting the text to get the text from the Tostring
                String name = Name.getText().toString();
                String price = Price.getText().toString();
                // Creating new fruit
                Fruit fruit = new Fruit(Name, Price);
                String FruitJson = gson.toJson(fruit);
                // Printing out fruits in json format
                System.out.println(FruitJson);
                // Creating params for the api key
                params.put("apikey", "032794ed45");
                // Creating string url and setting the url to delete the fruit
                String url = "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json";
                new insertFruit().execute(); // Calling the insert fruit function method in the async task
            } // Close public void view
        }); // Close on click listener for the button
    }

    // Creating public string method for performPostcall
    public String performPostcall(String requestURL, HashMap<String, String> postDataParams) {
        // URL variable
        URL url;
        // Creating empty string for the response
        String response = "";
        // Creating try for the url connection
        try {
            url = new URL(requestURL); // Request url
            // Creating the connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Creating read time out
            conn.setReadTimeout(15000);
            // Creating connect time out
            conn.setConnectTimeout(15000);
            // Setting the request method for post
            conn.setRequestMethod("POST");
            // Setting the do input boolean value as true
            conn.setDoInput(true);
            // Setting the do output a boolean value as true
            conn.setDoOutput(true);

            // Write/send/post data to the connection using output stream and bufferedwriter
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            // Write/send/post key/value data (url encoded) to the server
            writer.write(getPostDataString(postDataParams));

            // Close the writer and flushing the writer
            writer.flush();
            writer.close();

            // Close the output stream
            os.close();

            // Get the server response code to determine what to do next (i.e. success/error)
            int responseCode = conn.getResponseCode();

            // Print the response code
            System.out.println("responseCode = " + responseCode);


            // Creating if statement for response code
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Creating string line
                String line;
                // Creating buffered reader
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                // Creating while loop for read line
                while ((line = br.readLine()) != null) {
                    response += line;
                } // Close while loop for readline
            } // Close if statement for http url connection
            // Otherwise
            else {
                // Print response
                response = "";
            } // Close else
        } // Close try
        // Creating catch tor the ProtocolException
        catch (ProtocolException e) {
            e.printStackTrace(); // Print stack trace
        } // Close catch
        // Creating catch for UnsupportedEncodingException
        catch (UnsupportedEncodingException e) {
            e.printStackTrace(); // Print stack trace
        } // Close catch for UnsupportedEncodingException
        // Creating MalformedURLException
        catch (MalformedURLException e) {
            e.printStackTrace(); // Print stack trace
        } // Close catch for MalformedURLException
        // Creating IOException
        catch (IOException e) {
            e.printStackTrace(); // Print stack trace
        } // Close IOException

        // Print the response
        System.out.println("response = " + response);
        // Return response
        return response;
    } // Close public String performPostcall

    // Creating private string method for getPostdataString
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        // Creating new string builder
        StringBuilder result = new StringBuilder();
        // Creating boolean first equal to true
        boolean first = true;

        // Creating for loop for map
        for (Map.Entry<String, String> entry : params.entrySet()) {
            // Creating if statement for first
            if (first)
                // Creating variable name for first equal to false
                first = false;
                // Otherwise
            else
                // Result append
                result.append("&");

            // Creating result.append for the url encoder to get the key
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            // Result append for equal
            result.append("=");
            // Creating result.append for the url encoder to get the value
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        } // Close if statement for first
        // Return the result
        return result.toString();
    } // Close private string method for getPostDataString


    // Creating Insert fruit class for the async task
    class insertFruit extends AsyncTask<Void, Void, Void> {
        // Creating protected void do in background to run the network call in the background
        protected Void doInBackground(Void... voids) {
            // Running the add fruit api network call
            performPostcall("https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json", params);
            return null; // Return null
        } // Close protected void doInbackground
    } // Close insert student class for the async task
} // Close add fruit activity class