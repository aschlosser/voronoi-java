package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.beachline.LeafBeachNode;
import de.alsclo.voronoi.util.Point;
import lombok.val;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

public class SiteEvent extends Event {

    private final Point sitePoint;

    public SiteEvent(Point sitePoint) {
        super(sitePoint.y);
        this.sitePoint = sitePoint;
    }

    @Override
    public Collection<Event> handle(Beachline beachline) {
        LeafBeachNode leaf = beachline.insertArc(sitePoint.x, sitePoint.y);
        // l2 -> l1 -> leaf <- r1 <- r2
        Optional<LeafBeachNode> l1 = leaf.getLeftNeightbor();
        Optional<LeafBeachNode> l2 = l1.flatMap(LeafBeachNode::getLeftNeightbor);
        Optional<LeafBeachNode> r1 = leaf.getRightNeightbor();
        Optional<LeafBeachNode> r2 = r1.flatMap(LeafBeachNode::getRightNeightbor);

        val events = new LinkedList<Event>();

        if (l2.isPresent()) {
            events.add(new VertexEvent(l2.get(), l1.get(), leaf));
        }
        if (l1.isPresent() && r1.isPresent()) {
            events.add(new VertexEvent(l1.get(), leaf, r1.get()));
        }
        if (r2.isPresent()) {
            events.add(new VertexEvent(leaf, r1.get(), r2.get()));
        }

        return events;
    }
}
