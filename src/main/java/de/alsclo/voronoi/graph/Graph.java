package de.alsclo.voronoi.graph;

import javafx.collections.transformation.SortedList;
import lombok.EqualsAndHashCode;

import java.util.*;

@EqualsAndHashCode
public class Graph {

    private final BisectorMap edges = new BisectorMap();
    private final Set<Vertex> vertices = new HashSet<>();

    public void addEdge(Edge e) {
        edges.put(e.getSite1(), e.getSite2(), e);
    }

    public Optional<Edge> getEdgeBetweenSites(Point a, Point b) {
        return Optional.ofNullable(edges.get(a, b));
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph[");
        edges.values().stream().map(e -> String.format("(%s,%s),\n", e.getA(), e.getB())).sorted().forEachOrdered(sb::append);
        sb.append("]");
        return sb.toString();
    }


}
