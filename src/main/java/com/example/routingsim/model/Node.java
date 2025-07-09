package com.example.routingsim.model;

/**
 * Represents a single node (or vertex) in the graph.
 * Each node has a unique ID and a position (x, y) on the screen,
 * which helps in visualizing it on the JavaFX canvas.
 */
public class Node {
    // Unique identifier for this node, like "N1", "N2", etc.
    private final String id;

    // The x and y coordinates for positioning the node on the canvas
    private double x, y;

    /**
     * Constructor to create a node with its ID and position.
     * @param id A unique name for the node (e.g., "N1").
     * @param x  X-coordinate on the canvas.
     * @param y  Y-coordinate on the canvas.
     */
    public Node(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    // Returns the ID of the node
    public String getId() {
        return id;
    }

    // Returns the X coordinate
    public double getX() {
        return x;
    }

    // Returns the Y coordinate
    public double getY() {
        return y;
    }

    /**
     * Updates the node's position on the canvas.
     * @param x New x-coordinate
     * @param y New y-coordinate
     */
    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
