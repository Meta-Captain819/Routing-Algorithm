package com.example.routingsim.model;

import javafx.beans.property.SimpleStringProperty;

public class LogEntry {
    public final SimpleStringProperty time = new SimpleStringProperty();
    public final SimpleStringProperty event = new SimpleStringProperty();
    public final SimpleStringProperty detail = new SimpleStringProperty();

    public LogEntry(String t, String e, String d) {
        time.set(t); event.set(e); detail.set(d);
    }
}
