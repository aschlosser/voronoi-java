package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import lombok.val;

import java.util.Collection;

public class SiteEvent extends Event {

    public SiteEvent(Point point) {
        super(point);
    }

    @Override
    public void handle(Collection<Event> eventQueue, Beachline beachline, Graph graph) {
        val result = beachline.insertArc(getPoint());
        result.splitLeaf.ifPresent(l -> graph.addEdge(new Edge(l.getSite(), getPoint())));
        result.splitLeaf.ifPresent(l -> l.getSubscribers().forEach(eventQueue::remove));
        result.newLeaf.addCircleEvents(eventQueue::add, getPoint().y);
    }
}
