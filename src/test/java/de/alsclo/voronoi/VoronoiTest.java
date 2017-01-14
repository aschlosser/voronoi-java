package de.alsclo.voronoi;

import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import de.alsclo.voronoi.graph.Vertex;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class VoronoiTest {

    @Test
    public void test() {
        val p1 = new Point(0.5, 1.5);
        val p2 = new Point(1.5, 1.5);
        val p3 = new Point(1.5, 0.5);
        val p4 = new Point(0.5, 0.5);

        val c = (new Point(1, 1));

        Voronoi diagram = new Voronoi(2, 2, Arrays.asList(c, p1, p2, p3, p4));

        Graph referenceGraph = new Graph();
        referenceGraph.addEdge(new Edge(p1, p2));
        referenceGraph.addEdge(new Edge(p2, p3));
        referenceGraph.addEdge(new Edge(p3, p4));
        referenceGraph.addEdge(new Edge(p4, p1));
        referenceGraph.addEdge(new Edge(p1, c));
        referenceGraph.addEdge(new Edge(p2, c));
        referenceGraph.addEdge(new Edge(p3, c));
        referenceGraph.addEdge(new Edge(p4, c));
        Vertex v1 = new Vertex(new Point(1.0, 1.5));
        referenceGraph.addVertex(v1);
        referenceGraph.getEdgeBetweenSites(p1, p2).get().addVertex(v1);
        referenceGraph.getEdgeBetweenSites(p1, c).get().addVertex(v1);
        referenceGraph.getEdgeBetweenSites(p2, c).get().addVertex(v1);
        Vertex v2 = new Vertex(new Point(1.5, 1.0));
        referenceGraph.addVertex(v2);
        referenceGraph.getEdgeBetweenSites(p2, p3).get().addVertex(v2);
        referenceGraph.getEdgeBetweenSites(p2, c).get().addVertex(v2);
        referenceGraph.getEdgeBetweenSites(p3, c).get().addVertex(v2);
        Vertex v3 = new Vertex(new Point(1.0, 0.5));
        referenceGraph.addVertex(v3);
        referenceGraph.getEdgeBetweenSites(p3, p4).get().addVertex(v3);
        referenceGraph.getEdgeBetweenSites(p3, c).get().addVertex(v3);
        referenceGraph.getEdgeBetweenSites(p4, c).get().addVertex(v3);
        Vertex v4 = new Vertex(new Point(0.5, 1.0));
        referenceGraph.addVertex(v4);
        referenceGraph.getEdgeBetweenSites(p4, p1).get().addVertex(v4);
        referenceGraph.getEdgeBetweenSites(p4, c).get().addVertex(v4);
        referenceGraph.getEdgeBetweenSites(p1, c).get().addVertex(v4);

        assertEquals(referenceGraph, diagram.getGraph());
    }

    @Test
    public void testRejection() {
        val points = new ArrayList<Point>();
        points.add(new Point(10, 0));
        points.add(new Point(0, 10));

        try {
            new Voronoi(3, 3, points);
            fail();
        } catch (IllegalArgumentException ignored) {

        }
    }

}
