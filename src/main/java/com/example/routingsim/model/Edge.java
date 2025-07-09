package com.example.routingsim.model;

/**
 * Represents an edge (or connection) between two nodes in the graph.
 * Each edge can have a weight, which may represent distance, cost, or time.
 * This class helps simulate weighted or unweighted network connections.
 */
public class Edge {
    // The node where this edge starts
    private final Node from;

    // The node where this edge ends
    private final Node to;

    // The weight of the edge (used in pathfinding algorithms)
    private double weight;

    /**
     * Constructor to create an edge between two nodes with a specific weight.
     * @param from    Starting node of the edge
     * @param to      Ending node of the edge
     * @param weight  Cost or distance between the two nodes
     */
    public Edge(Node from, Node to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    // Returns the starting node of the edge
    public Node getFrom() {
        return from;
    }

    // Returns the ending node of the edge
    public Node getTo() {
        return to;
    }

    // Returns the weight assigned to this edge
    public double getWeight() {
        return weight;
    }

    // Updates the weight of this edge (e.g., if cost or distance changes)
    public void setWeight(double w) {
        this.weight = w;
    }
}
