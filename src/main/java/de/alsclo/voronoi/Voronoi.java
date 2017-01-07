package de.alsclo.voronoi;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.event.Event;
import de.alsclo.voronoi.event.SiteEvent;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import lombok.val;

import java.util.Collection;
import java.util.PriorityQueue;

public class Voronoi {

    public Voronoi(double width, double height, Collection<Point> points) {
        points.stream().filter(p -> p.x < 0 || p.x > width || p.y < 0 || p.y > height).findAny()
                .ifPresent(p -> {throw new IllegalArgumentException(p + " lies outside the bounding box.");});
        val queue = new PriorityQueue<Event>();
        points.stream().map(SiteEvent::new).forEach(queue::offer);

        val beachline = new Beachline();
        val graph = new Graph();
        while(!queue.isEmpty()) {
            queue.poll().handle(beachline, graph).forEach(queue::add);
        }
    }

}
