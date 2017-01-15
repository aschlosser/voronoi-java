package de.alsclo.voronoi;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.event.Event;
import de.alsclo.voronoi.event.SiteEvent;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import lombok.Getter;
import lombok.val;

import java.util.Collection;
import java.util.PriorityQueue;

public class Voronoi {

    @Getter
    private final Graph graph = new Graph();

    public Voronoi(double width, double height, Collection<Point> points) {
        points.stream().filter(p -> p.x < 0 || p.x > width || p.y < 0 || p.y > height).findAny().ifPresent(p -> {
            throw new IllegalArgumentException(p + " lies outside the bounding box.");
        });
        val queue = new PriorityQueue<Event>();
        points.stream().map(SiteEvent::new).forEach(queue::offer);
        points.forEach(graph::addSite);

        val beachline = new Beachline();
        double sweep = Double.MAX_VALUE;
        while (!queue.isEmpty()) {
            val e = queue.peek();
            assert e.getPoint().y <= sweep;
            e.handle(beachline, graph).forEach(queue::add);
            queue.remove(e);
            sweep = e.getPoint().y;
        }
    }

}
