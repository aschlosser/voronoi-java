package de.alsclo.voronoi;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.event.Event;
import de.alsclo.voronoi.event.SiteEvent;
import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import de.alsclo.voronoi.graph.Vertex;
import lombok.Getter;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Voronoi {

    @Getter
    private final Graph graph = new Graph();

    public Voronoi(Collection<Point> points) {
        val queue = new PriorityQueue<Event>();
        points.stream().map(SiteEvent::new).forEach(queue::offer);
        points.forEach(graph::addSite);

        val beachline = new Beachline();
        double sweep = Double.MAX_VALUE;
        while (!queue.isEmpty()) {
            val e = queue.peek();
            assert e.getPoint().y <= sweep;
            e.handle(queue, beachline, graph);
            queue.remove(e);
            sweep = e.getPoint().y;
        }
    }

    public void applyBoundingBox(double x, double y, double width, double height) {
        getGraph().getSitePoints().stream()
                .filter(p -> p.x < x || p.x > x + width || p.y < y || p.y > y + height).findAny().ifPresent(p -> {
            throw new IllegalArgumentException("Site " + p + " lies outside the bounding box.");
        });
        //TODO
    }

    public Voronoi relax() {
        Map<Point, Set<Edge>> edges = new HashMap<>();
        graph.getSitePoints().forEach(p -> edges.put(p, new HashSet<>()));
        graph.edgeStream().forEach(e -> {
            edges.get(e.getSite1()).add(e);
            edges.get(e.getSite2()).add(e);
        });
        List<Point> newPoints = graph.getSitePoints().stream().map(site -> {
            Set<Vertex> vertices = Stream.concat(edges.get(site).stream().map(Edge::getA), edges.get(site).stream().map(Edge::getB)).collect(Collectors.toSet());
            if (vertices.isEmpty() || vertices.contains(null)) {
                return site;
            } else {
                double avgX = vertices.stream().mapToDouble(v -> v.getLocation().x).average().getAsDouble();
                double avgY = vertices.stream().mapToDouble(v -> v.getLocation().y).average().getAsDouble();
                return new Point(avgX, avgY);
            }
        }).collect(Collectors.toList());
        return new Voronoi(newPoints);
    }

}
