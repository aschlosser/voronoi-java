package de.alsclo.voronoi.graph;

import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode
public class Graph {

    private final BisectorMap edges = new BisectorMap();
    private final Set<Vertex> vertices = new HashSet<>();
    private final Set<Point> sites = new HashSet<>();

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

    public void addSite(Point newSite) {
        sites.add(newSite);
    }

    public Set<Point> getSitePoints() {
        return Collections.unmodifiableSet(sites);
    }

}
