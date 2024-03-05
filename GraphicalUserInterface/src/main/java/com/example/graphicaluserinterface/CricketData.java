package com.example.graphicaluserinterface;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

// This class represents a single row of cricket data, with properties for country, ODI, T20, and Test statistics
public class CricketData {
    // Properties for country name, ODI, T20, and Test statistics
    private final StringProperty country;
    private final IntegerProperty odi;
    private final IntegerProperty t20;
    private final IntegerProperty test;

    // Constructor to initialize cricket data with values for country, ODI, T20, and Test
    public CricketData(String country, int odi, int t20, int test) {
        this.country = new SimpleStringProperty(country);
        this.odi = new SimpleIntegerProperty(odi);
        this.t20 = new SimpleIntegerProperty(t20);
        this.test = new SimpleIntegerProperty(test);
    }

    // Getter method for the country property
    public StringProperty countryProperty() {
        return country;
    }

    // Getter method for the ODI property
    public IntegerProperty odiProperty() {
        return odi;
    }

    // Getter method for the T20 property
    public IntegerProperty t20Property() {
        return t20;
    }

    // Getter method for the Test property
    public IntegerProperty testProperty() {
        return test;
    }
}
