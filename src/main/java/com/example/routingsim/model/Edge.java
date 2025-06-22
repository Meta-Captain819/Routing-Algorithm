package com.example.routingsim.model;

public class Edge {
    private final Node from, to;
    private double weight;

    public Edge(Node from, Node to, double weight) {
        this.from = from; this.to = to; this.weight = weight;
    }

    public Node getFrom() { return from; }
    public Node getTo() { return to; }
    public double getWeight() { return weight; }
    public void setWeight(double w) { this.weight = w; }
}
