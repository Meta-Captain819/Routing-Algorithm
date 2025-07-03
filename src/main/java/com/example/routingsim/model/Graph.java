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

public class Graph {

    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<Node, List<Edge>> adj = new HashMap<>();

    public Node addNode(String id, double x, double y) {
        Node n = new Node(id, x, y);
        nodes.put(id, n);
        adj.put(n, new ArrayList<>());
        return n;
    }

    public void addEdge(String fromId, String toId, double w) {
        Node f = nodes.get(fromId);
        Node t = nodes.get(toId);

        // ── 1. look for an existing edge  ─────────────────────────────
        Edge existingFT = findEdge(f, t);
        Edge existingTF = findEdge(t, f);        // reverse

        if (existingFT != null && existingTF != null) {
            // → just update the weight
            existingFT.setWeight(w);
            existingTF.setWeight(w);
            return;          // nothing else to add
        }

        // ── 2. otherwise create new edges  ─────────────────────────
        Edge e = new Edge(f, t, w);
        adj.get(f).add(e);
        adj.get(t).add(new Edge(t, f, w)); // undirected
    }

    /**
     * helper
     */
    private Edge findEdge(Node a, Node b) {
        return adj.getOrDefault(a, List.of()).stream()
                .filter(edge -> edge.getTo().equals(b))
                .findFirst()
                .orElse(null);
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public List<Edge> getEdges(Node n) {
        return adj.get(n);
    }

    public List<Node> findShortestPath(Node source, Node destination) {
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(destination)) {
                // Reached destination
                List<Node> path = new ArrayList<>();
                Node step = destination;
                while (step != null) {
                    path.add(step);
                    step = parent.get(step);
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

   /** Removes a node and *all* incident edges from the graph model */
public void removeNode(Node n) {
    // 1. For each neighbor, remove the edge pointing to 'n'
    List<Edge> incident = new ArrayList<>(adj.getOrDefault(n, List.of()));
    for (Edge e : incident) {
        Node neighbor = e.getTo();
        adj.get(neighbor).removeIf(edge -> edge.getTo().equals(n));
    }
    // 2. Remove the node's own edge list and map entry
    adj.remove(n);
    nodes.remove(n.getId());
}


public void removeEdge(Node a, Node b) {
    adj.get(a).removeIf(e -> e.getTo().equals(b));
    adj.get(b).removeIf(e -> e.getTo().equals(a));
}

/** -------------------------------------------------------------------
 *  Dijkstra: returns the lowest-total-weight path from source to dest.
 *  If no path exists, returns null.
 *  ------------------------------------------------------------------ */
public List<Node> findWeightedShortestPath(Node source, Node destination) {
    // distance map
    Map<Node, Double> dist = new HashMap<>();
    // parent (predecessor) map to reconstruct the path
    Map<Node, Node> parent = new HashMap<>();
    // priority queue ordered by current distance
    PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

    // initialise distances
    for (Node n : nodes.values()) dist.put(n, Double.POSITIVE_INFINITY);
    dist.put(source, 0.0);
    pq.add(source);

    while (!pq.isEmpty()) {
        Node u = pq.poll();
        if (u.equals(destination)) break;  // found the best path

        for (Edge e : adj.getOrDefault(u, List.of())) {
            Node v = e.getTo();
            double alt = dist.get(u) + e.getWeight();
            if (alt < dist.get(v)) {
                dist.put(v, alt);
                parent.put(v, u);
                pq.remove(v);  // update priority if v already in queue
                pq.add(v);
            }
        }
    }

    // unreachable?
    if (!parent.containsKey(destination) && !source.equals(destination)) return null;

    // reconstruct path
    List<Node> path = new ArrayList<>();
    for (Node step = destination; step != null; step = parent.get(step)) {
        path.add(step);
    }
    Collections.reverse(path);
    return path;
}



}
