package com.example.routingsim.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a single log entry in the simulation log table.
 * Each entry contains the time, type of event, and a brief detail.
 */
public class LogEntry {

    // Observable string properties for each column in the table
    public final SimpleStringProperty time   = new SimpleStringProperty(); // When the event happened
    public final SimpleStringProperty event  = new SimpleStringProperty(); // Type or source of the event
    public final SimpleStringProperty detail = new SimpleStringProperty(); // Description of what happened

    // Constructor that fills the log entry with provided data
    public LogEntry(String t, String e, String d) {
        time.set(t);         // Set the time (e.g., "12:45:30")
        event.set(e);        // Set the event source/type (e.g., "Sim:Start")
        detail.set(d);       // Set the description (e.g., "Route initiated from N1")
    }
}
