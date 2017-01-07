package de.alsclo.voronoi.graph;

import java.util.Optional;

public class Graph {

    private final BisectorMap edges = new BisectorMap();

    public void addEdge(Edge e) {
        edges.put(e.getSite1(), e.getSite2(), e);
    }

    public Optional<Edge> getEdgeBetween(Point a, Point b) {
        return Optional.ofNullable(edges.get(a, b));
    }
}
