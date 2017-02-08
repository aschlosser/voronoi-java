package de.alsclo.voronoi.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Vertex {
    @Getter
    private final Point location;

    @Override
    public String toString() {
        return location.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return location.equals(vertex.location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
