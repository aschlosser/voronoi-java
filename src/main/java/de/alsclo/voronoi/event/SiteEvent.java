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
        super(sitePoint.y);
        this.sitePoint = sitePoint;
    }

    @Override
    public Collection<Event> handle(Beachline beachline, Graph graph) {
        val result = beachline.insertArc(sitePoint.x, sitePoint.y);
        result.splitLeaf.ifPresent(l -> graph.addEdge(new Edge(new Point(l.getSiteX(), l.getSiteY()), sitePoint)));

        // l2 -> l1 -> leaf <- r1 <- r2
        Optional<LeafBeachNode> l1 = result.newLeaf.getLeftNeighbor();
        Optional<LeafBeachNode> l2 = l1.flatMap(LeafBeachNode::getLeftNeighbor);
        Optional<LeafBeachNode> r1 = result.newLeaf.getRightNeighbor();
        Optional<LeafBeachNode> r2 = r1.flatMap(LeafBeachNode::getRightNeighbor);

        val events = new LinkedList<Event>();

        if (l2.isPresent()) {
            events.add(new VertexEvent(l2.get(), l1.get(), result.newLeaf));
        }
        if (l1.isPresent() && r1.isPresent()) {
            events.add(new VertexEvent(l1.get(), result.newLeaf, r1.get()));
        }
        if (r2.isPresent()) {
            events.add(new VertexEvent(result.newLeaf, r1.get(), r2.get()));
        }

        return events;
    }
}
