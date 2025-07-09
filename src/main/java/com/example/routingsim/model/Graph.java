package com.example.routingsim.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * This class represents the core graph structure used in the routing simulator.
 * It supports creating and managing nodes and edges, as well as running
 * pathfinding algorithms like BFS (for shortest hop path) and Dijkstra (for weighted path).
 */
public class Graph {

    // Stores all the nodes using their ID as the key
    private final Map<String, Node> nodes = new HashMap<>();

    // Stores the adjacency list for each node (each node maps to a list of its connected edges)
    private final Map<Node, List<Edge>> adj = new HashMap<>();

    /**
     * Adds a new node to the graph with a unique ID and a position (x, y).
     * @param id Unique identifier for the node
     * @param x X coordinate
     * @param y Y coordinate
     * @return The created node
     */
    public Node addNode(String id, double x, double y) {
        Node n = new Node(id, x, y);
        nodes.put(id, n);
        adj.put(n, new ArrayList<>());
        return n;
    }

    /**
     * Adds an undirected edge between two nodes with a given weight.
     * If the edge already exists, its weight is simply updated.
     */
    public void addEdge(String fromId, String toId, double w) {
        Node f = nodes.get(fromId);
        Node t = nodes.get(toId);

        // Check if edge already exists in both directions (since undirected)
        Edge existingFT = findEdge(f, t);
        Edge existingTF = findEdge(t, f);

        if (existingFT != null && existingTF != null) {
            // If yes, just update the weights in both directions
            existingFT.setWeight(w);
            existingTF.setWeight(w);
            return;
        }

        // Otherwise, create new edges in both directions
        adj.get(f).add(new Edge(f, t, w));
        adj.get(t).add(new Edge(t, f, w));
    }

    /**
     * Helper method to find an existing edge from node a to node b.
     */
    private Edge findEdge(Node a, Node b) {
        return adj.getOrDefault(a, List.of()).stream()
                .filter(edge -> edge.getTo().equals(b))
                .findFirst()
                .orElse(null);
    }

    // Returns all nodes in the graph
    public Collection<Node> getNodes() {
        return nodes.values();
    }

    // Returns all edges connected to a specific node
    public List<Edge> getEdges(Node n) {
        return adj.get(n);
    }

    /**
     * Finds the shortest path (by number of hops) from source to destination
     * using Breadth-First Search (BFS).
     */
    public List<Node> findShortestPath(Node source, Node destination) {
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(destination)) {
                // Reconstruct path once destination is found
                List<Node> path = new ArrayList<>();
                for (Node step = destination; step != null; step = parent.get(step)) {
                    path.add(step);
                }
                Collections.reverse(path);
                return path;
            }

            for (Edge edge : adj.getOrDefault(current, new ArrayList<>())) {
                Node neighbor = edge.getTo();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return null; // No path found
    }

    /**
     * Removes a node from the graph and also removes any edges connected to it.
     */
    public void removeNode(Node n) {
        // Remove all edges that point to this node
        List<Edge> incident = new ArrayList<>(adj.getOrDefault(n, List.of()));
        for (Edge e : incident) {
            Node neighbor = e.getTo();
            adj.get(neighbor).removeIf(edge -> edge.getTo().equals(n));
        }

        // Remove the node itself
        adj.remove(n);
        nodes.remove(n.getId());
    }

    /**
     * Removes the edge between two nodes (in both directions, since undirected).
     */
    public void removeEdge(Node a, Node b) {
        adj.get(a).removeIf(e -> e.getTo().equals(b));
        adj.get(b).removeIf(e -> e.getTo().equals(a));
    }

    /**
     * Uses Dijkstra’s algorithm to find the lowest-weight path
     * from the source node to the destination node.
     * Returns null if no path exists.
     */
    public List<Node> findWeightedShortestPath(Node source, Node destination) {
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> parent = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        // Initialize distances
        for (Node n : nodes.values()) {
            dist.put(n, Double.POSITIVE_INFINITY);
        }
        dist.put(source, 0.0);
        pq.add(source);

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            if (u.equals(destination)) break; // Reached the goal

            for (Edge e : adj.getOrDefault(u, List.of())) {
                Node v = e.getTo();
                double alt = dist.get(u) + e.getWeight();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    parent.put(v, u);
                    pq.remove(v); // Refresh the node in the priority queue
                    pq.add(v);
                }
            }
        }

        // No path found
        if (!parent.containsKey(destination) && !source.equals(destination)) {
            return null;
        }

        // Reconstruct the shortest path
        List<Node> path = new ArrayList<>();
        for (Node step = destination; step != null; step = parent.get(step)) {
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }
}
