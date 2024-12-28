package com.example.fruitdetailsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteFruitActivity extends AppCompatActivity {
    final HashMap<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_fruit);

        // Creating bundle to get extras
        Bundle extras = getIntent().getExtras();
        // Creating a student object from the fruit object that was passed over from
        // the MainActivity. Notice you use the key ('fruit') to retrieve the value/variable needed.
        Fruit fruit = (Fruit) extras.get("fruit");

        // Creating text view to findViewById to view the different student information on the app
        final EditText Name = (EditText) findViewById(R.id.DeleteFruitName);
        final EditText Price = (EditText) findViewById(R.id.DeleteFruitPrice);

        // Instantiating the button for delete fruit
        Button DeleteFruitButton = (Button) findViewById(R.id.buttonConfirmDelete);

        // Setting the text for the text view
        Name.setText((CharSequence) Fruit.Name);
        Price.setText(Fruit.getPrice());


        // Creating on click listener for the DeleteFruitButton
        DeleteFruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
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
                // Creating param for fruit name to delete the fruit
                params.put("name", String.valueOf(Fruit.getName()));
                // Creating string url and setting the url to delete the fruit
                String url = "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json";
                new deleteFruit().execute();
            } // close public void onClick
        }); // Close on click listener for delete fruit button
    }

    // Creating public string for performPostCall and creating string requestURL, hasmap string and postDataParams
    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        // URL variable
        URL url;
        // Creating empty string for the response
        String response = "";
        // Creating try for the pose request
        try {
            // Creating url variable and requesting url
            url = new URL(requestURL);

            // Creating the connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Creating connection for read time out and setting it to 15000
            conn.setReadTimeout(15000);
            // Creating set time out connection
            conn.setConnectTimeout(15000);
            // Creating request method for post
            conn.setRequestMethod("POST");
            // Creating do input for the connection and setting it boolean value of true
            conn.setDoInput(true);
            // Creating do output connection and setting it boolean value as true
            conn.setDoOutput(true);

            // Posting the data to the connection using OutputStream and BufferedWriter
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            // Post value data encoded
            // Printing the post data string
            System.out.println(getPostDataString(postDataParams));
            writer.write(getPostDataString(postDataParams));
            // Flushing the writer
            writer.flush();
            // Close the writer
            writer.close();
            // Closing the output stream
            os.close();

            // Getting the server reponse code
            int responseCode = conn.getResponseCode();
            // Printing out the response code
            System.out.println("responseCode = " + responseCode);
            // Creating if statement for the response code
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                // Creating new buffered reader and getting input stream
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                // Creating while loop for readline not equal to null
                while ((line = br.readLine()) != null) {
                    response += line; // Adding line
                } // Close while loop for line
            } // Close if statment for responce code
            // Otherwise print out error message
            else {
                // Empty response
                response = "";
            } // Close else
        } // Close try
        // Creating catch exception e
        catch (Exception e) {
            e.printStackTrace();
        } // Close catch exception e
        // Print response
        System.out.println("response = " + response);
        return response; // Return the response
    } // Close public string performPostCall

    // Creating private string for get post data string
    private String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException {
        // Creating string buffer and creating new string builder
        StringBuilder result = new StringBuilder();

        boolean first = true; // Creating boolean first and setting it a value as true
        // Creating for loop for the Map.Entry
        for (Map.Entry<String, String> entry : params.entrySet()) {
            // Creating if statement for first
            if (first)
                first = false; // Creating variable name for first and setting it boolean value as false
            else // Otherwise rsult.append (&)
                result.append("&");
            // Creating result.append to get the entry key
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            // Result.append for equal
            result.append("=");
            // Creating result.append to get the value
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        } // Close for loop for map
        return result.toString(); // Return the result
    } // Close get post data string


    // Creating class for delete fruit for the async task
    class deleteFruit extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... voids) {
            // Performing post call for the fruit api for the delete student
            performPostCall("https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json", params);
            return null; // Return null
        } // Close protected void DoInBackground
    } // Close delete student class for the async task
}