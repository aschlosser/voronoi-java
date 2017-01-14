package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.beachline.LeafBeachNode;
import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import lombok.val;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

public class SiteEvent extends Event {

    private final Point sitePoint;

    public SiteEvent(Point sitePoint) {
        super(sitePoint);
        this.sitePoint = sitePoint;
    }

    @Override
    public Collection<Event> handle(Beachline beachline, Graph graph) {
        val result = beachline.insertArc(sitePoint);
        result.splitLeaf.ifPresent(l -> graph.addEdge(new Edge(l.getSite(), sitePoint)));
        return result.newLeaf.checkCircleEvents();
    }
}
