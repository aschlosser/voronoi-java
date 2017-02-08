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

    @Override
    public String toString() {
        return "Edge("+a+", "+b+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (!site1.equals(edge.site1)) return false;
        if (!site2.equals(edge.site2)) return false;
        if (a != null ? !a.equals(edge.a) : edge.a != null) return false;
        return b != null ? b.equals(edge.b) : edge.b == null;
    }

    @Override
    public int hashCode() {
        int result = site1.hashCode();
        result = 31 * result + site2.hashCode();
        result = 31 * result + (a != null ? a.hashCode() : 0);
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
