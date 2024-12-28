package com.example.fruitdetailsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Creating string object for all fruits, to store all fruits into array
    String[] Fruit;
    ListView FruitList;
    Button buttonAddFruit;
    Button AsyncTask;
    Context c = this;

    // Creating array to store all fruits
    ArrayList<Fruit> allFruits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FruitList = (ListView) findViewById(R.id.FruitList);

        Button buttonAddFruit = (Button) findViewById(R.id.buttonAddFruit);

        new getData().execute(); // Calling the GetData function for the async task


        // Creating on click listener for AddFruit button
        buttonAddFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            // creating public void onClick
            public void onClick(View view) {
                // Creating intent and linking it to the add fruit activity class
                Intent intent1 = new Intent(getApplicationContext(), AddFruitActivity.class);
                startActivity(intent1); // Starting the activity
            } // close public void onClick
        }); // Close setOnClickListener for button add fruit

        // Creating FruitList for set on item click listener
        FruitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long
                    l) {
                // Creating toast for the fruit list
                Toast.makeText(getApplicationContext(), "you pressed " +
                        allFruits.get(i).getName(), Toast.LENGTH_SHORT).show(); // getting all fruits
                // Declaring a new intent and give it the context and
                // specify which activity you want to open/start
                Intent intent = new Intent(getApplicationContext(), FruitDetailsActivity.class);
                // Add/put the selected fruit object into the intent
                intent.putExtra("fruit", allFruits.get(i));
                // Launch the activity
                startActivity(intent);
            } // close public void
        }); // Close setOnItemClickListener for student list
    }

    // Creating public string for convertStreamToString
    public String convertStreamToString(InputStream is) {
        // Creating java.util.Scanner
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : ""; // Return the s.hasNext
    } // Close public string convertStreamToString

    // Class for get data for the async task
    class getData extends android.os.AsyncTask<Void, Void, ArrayList<Fruit>> {
        // Creating protected array list for fruit
        protected ArrayList<Fruit> doInBackground(Void... voids) {
            // Creating array list to store all fruits in array
            ArrayList<Fruit> fruits = new ArrayList<Fruit>();

            // Creating http url connection variable
            HttpURLConnection urlConnection;
            InputStream in = null;
            // Get json data using http request
            try {
                URL url = new URL("https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json");
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            } // Close catch
            // Creating string respose
            String response = convertStreamToString(in);
            // Print the server response
            System.out.println("Server response = " + response);

            // Creating try for the jsonArray
            try {
                // Declaring a new json array and pass it the string response from the server.
                // This will convert the string into a JSON array which we can then iterate
                // over using a loop
                JSONArray jsonArray = new JSONArray(response);
                Fruit = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    // The following line of code will get the name of the fruits from the
                    // current JSON object and store it in a string variable called name
                    String Name = jsonArray.getJSONObject(i).get("Name").toString();
                    String Price = jsonArray.getJSONObject(i).get("Price").toString();

                    // Creating new fruit
                    Fruit fruit = new Fruit(Name, Price);
                    fruits.add(fruit); // Adding fruit

                } // Close for loop jsonArray
            } catch (JSONException e) { // Close for loop and starting catch IoException
                e.printStackTrace(); // PrintStackTrace
            } // Close catch Ioexception

            return fruits; // return fruits
        } // close protected array list for fruits

        // Creating protected void onPostExectue
        protected void onPostExecute(ArrayList<Fruit> fruits) {
            Fruit = new String[fruits.size()];
            for (int i = 0; i < fruits.size(); i++) {
                allFruits.add(fruits.get(i));
                Fruit[i] = fruits.get(i).getName();
            } // close for loop
            ArrayAdapter arrayAdapter = new ArrayAdapter(c, android.R.layout.simple_list_item_1, Fruit);
            FruitList.setAdapter(arrayAdapter);
        } // close protected void onPostExectue
    } // close get data async task class
} // Close main activity class