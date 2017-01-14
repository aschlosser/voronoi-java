package de.alsclo.voronoi.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Edge {
    @Getter
    private final Point site1, site2;
    @Getter
    private Vertex a, b;

    public void addVertex(Vertex v) {
        if (a == null) {
            a = v;
        } else if (b == null) {
            b = v;
        } else {
            throw new IllegalStateException("Trying to set a third vertex on an edge");
        }
    }
}
