package de.alsclo.voronoi.graph;

public class Vertex {
    private Point location;

    public Vertex(Point location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return location.toString();
    }
}
