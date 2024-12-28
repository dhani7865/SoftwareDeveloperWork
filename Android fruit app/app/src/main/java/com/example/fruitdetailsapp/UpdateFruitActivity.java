package com.example.fruitdetailsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;


public class UpdateFruitActivity extends AppCompatActivity {
    final HashMap<String, String> params = new HashMap<>();
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fruit);

        // Creating bundle extras
        Bundle extras = getIntent().getExtras();
        // Creating extras for the student and calling the Fruit class
        Fruit fruit = (Fruit) extras.get("fruit");


        // Instantiating the button for update button
        Button buttonUpdateFruit = (Button) findViewById(R.id.buttonUpdateFruit);

        // Creating final edit text to update fruit name
        final EditText Name = (EditText) findViewById(R.id.UpdateFruitName);
        // Creating final edit text to update fruit price
        final EditText Price = (EditText) findViewById(R.id.UpdateFruitPrice);
        // Setting the text and calling the students from the fruit class
        Name.setText(Fruit.Name);
        Price.setText(Fruit.getPrice());

        // Creating on click listener for the update button
        buttonUpdateFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            // creating public void on click
            public void onClick(View view) {
                // Creating gson object
                Gson gson = new Gson();
                // Creating fruit object called s
                String Name = "";
                String Price = "";

                Fruit f = new Fruit(Name, Price);
                // Converting student json string to json
                String FruitJson = gson.toJson(f);
                // Print student in json format
                System.out.println(FruitJson);
                // Putting the string into hashmap
                params.put("json", FruitJson);
                params.put("apikey", "032794ed45");
                // Url to update the fruit
                String url = "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json";

                new UpdateFruit().execute();
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
            // Setting the do input a boolean value as true
            conn.setDoInput(true);
            // Setting the do output a boolean value as true
            conn.setDoOutput(true);

            // Write/send/post data to the connection using output stream and bufferedwriter
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            // Write/send/post key/value data (url encoded) to the server
            writer.write(getPostDataString(postDataParams));

            // Flush the writer and close the writer
            writer.flush();
            writer.close();

            // Close the output stream
            os.close();

            // Get the server response code to determine what to do next (i.e. sucess/error)
            int responseCode = conn.getResponseCode();
            // Print the response code
            System.out.println("responseCode = " + responseCode);

            // If statement for response code
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
        // Creating catch for tor the ProtocolException
        catch (ProtocolException e) {
            e.printStackTrace(); // Print stack trace
        } // Close catch
        // Creating catch for UnsupportedEncodingException
        // Printing stack trace
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } // close catch for support encoding exception
        // Creating catch for malformed URl exception
        catch (MalformedURLException e) {
            e.printStackTrace(); // Print stack trace
        } // Close catch for malformed url exception
        // Creating catch for IOException
        catch (IOException e) {
            e.printStackTrace(); // Print stack trace
        } // Close catch for IOexception

        // Print the response
        System.out.println("response = " + response);
        // Return response
        return response;
    } // Close public String performPostcall

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        // Creating string builder
        StringBuilder result = new StringBuilder();
        // Creating boolean first equal to true
        boolean first = true;

        // Creating for loop for map
        for (Map.Entry<String, String> entry : params.entrySet()) {
            // Creating if statement for first
            if (first)
                // Creating variable name for first equal to false
                first = false;
                // Ctherwise
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
        // return the result
        return result.toString();
    } // Close private string method for getPostDataString


    // Creating class for the async class for the insert student.
    class UpdateFruit extends AsyncTask<Void, Void, Void> {
        // Creating protected void to do the network in the background
        protected Void doInBackground(Void... voids) {
            // Performing the post call for the url
            performPostcall("https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json", params);
            return null; // Return null
        } // Close protected void doInbackground
    } // Close update fruit class for the async task
}