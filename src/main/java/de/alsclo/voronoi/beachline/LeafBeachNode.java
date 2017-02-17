package de.alsclo.voronoi.beachline;

import de.alsclo.voronoi.event.Event;
import de.alsclo.voronoi.event.VertexEvent;
import de.alsclo.voronoi.graph.Point;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = false)
public class LeafBeachNode extends BeachNode {

    private final Point site;

    private final List<VertexEvent> subscribedEvents = new LinkedList<>();

    LeafBeachNode(Point site) {
        this.site = site;
    }

    private LeafBeachNode copy() {
        return new LeafBeachNode(site);
    }

    public Point getSite() {
        return site;
    }

    @Override
    public InsertionResult insertArc(Point newSite) {
        val newLeaf = new LeafBeachNode(newSite);
        if (newSite.y == site.y) {
            if (newSite.x < site.x) {
                replaceBy(new InnerBeachNode(newLeaf, copy()));
            } else {
                replaceBy(new InnerBeachNode(copy(), newLeaf));
            }
        } else {
            if (newSite.x < site.x) {
                replaceBy(new InnerBeachNode(new InnerBeachNode(copy(), newLeaf), copy()));
            } else {
                replaceBy(new InnerBeachNode(copy(), new InnerBeachNode(newLeaf, copy())));
            }
        }
        setParent(null); // Disconnect this node from the tree
        return new InsertionResult(Optional.of(this), newLeaf);
    }

    public void remove() {
        InnerBeachNode parent = getParent();
        BeachNode sibling = parent.getLeftChild() == this ? parent.getRightChild() : parent.getLeftChild();
        parent.replaceBy(sibling);
        setParent(null); // Disconnect this node from the tree
    }

    @Override
    public LeafBeachNode getLeftmostLeaf() {
        return this;
    }

    @Override
    public LeafBeachNode getRightmostLeaf() {
        return this;
    }

    @Override
    public Stream<LeafBeachNode> leafIterator() {
        return Stream.of(this);
    }

    public Optional<LeafBeachNode> getLeftNeighbor() {
        InnerBeachNode current = getParent();
        BeachNode child = this;
        if (current != null) {
            while (current.getParent() != null) {
                if (current.getRightChild() == child) {
                    return Optional.of(current.getLeftChild().getRightmostLeaf());
                } else {
                    child = current;
                    current = current.getParent();
                }
            }
        }
        return Optional.empty();
    }

    public Optional<LeafBeachNode> getRightNeighbor() {
        InnerBeachNode current = getParent();
        BeachNode child = this;
        if (current != null) {
            while (current.getParent() != null) {
                if (current.getLeftChild() == child) {
                    return Optional.of(current.getRightChild().getLeftmostLeaf());
                } else {
                    child = current;
                    current = current.getParent();
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<VertexEvent> buildEvent(LeafBeachNode center) {
        return center.getLeftNeighbor().flatMap(l -> center.getRightNeighbor().flatMap(r -> VertexEvent.build(l, center, r)));
    }

    public List<Event> checkCircleEvents(double sweepY) {
        // l2 -> l1 -> leaf <- r1 <- r2
        val events = new LinkedList<Event>();
        getLeftNeighbor().flatMap(LeafBeachNode::getLeftNeighbor).flatMap(LeafBeachNode::buildEvent).ifPresent(events::add);
        getLeftNeighbor().flatMap(LeafBeachNode::buildEvent).ifPresent(events::add);
        buildEvent(this).ifPresent(events::add);
        getRightNeighbor().flatMap(LeafBeachNode::buildEvent).ifPresent(events::add);
        getRightNeighbor().flatMap(LeafBeachNode::getRightNeighbor).flatMap(LeafBeachNode::buildEvent).ifPresent(events::add);
        return events;
    }

    public void subscribe(VertexEvent e) {
        subscribedEvents.add(e);
    }

    public void unsubscribe(VertexEvent e) {
        subscribedEvents.remove(e);
    }

    public List<VertexEvent> getSubscribers() {
        return new LinkedList<>(subscribedEvents);
    }
}