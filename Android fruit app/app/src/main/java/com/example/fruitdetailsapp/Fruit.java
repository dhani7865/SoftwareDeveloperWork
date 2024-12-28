package com.example.fruitdetailsapp;

import android.widget.EditText;

import java.io.Serializable;



// Creating public class for fruit which implements Serializable
public class Fruit implements Serializable {
    // Creating string variables for Name, price,
    static String Name;
    static String Price;

    // Creating string constructors for the different fields
    public Fruit(String Name, String Price) {
        this.Name = Name;
        this.Price = String.valueOf(Price);
    } // close constructor

    public Fruit(EditText name, EditText price) {
    }

    // Creating public void for setname and getname
    public void setName(String Name) {
        this.Name = Name;
    } // Close public void setname

    // Creating public string for getname, which would return the Name
    public static String getName() {
        return Name;
    } // Close public string getname

    // Creating public void for setPrice and getPrice
    public void setPrice(String Price) {
        this.Price = Price;
    }// close public void setPrice

    // Creating public string for get price, which would return the price
    public static String getPrice() {
        return Price;
    }// Close public void getPrice

} // Close public class fruit