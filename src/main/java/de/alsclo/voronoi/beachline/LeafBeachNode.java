package de.alsclo.voronoi.beachline;

import de.alsclo.voronoi.event.Event;
import de.alsclo.voronoi.event.VertexEvent;
import de.alsclo.voronoi.graph.Point;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(callSuper = false)
public class LeafBeachNode extends BeachNode {

    private final Point site;

    LeafBeachNode(Point site) {
        this.site = site;
    }

    private LeafBeachNode copy() {
        return new LeafBeachNode(site);
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
        val parent = getParent();
        val sibling = parent.getLeftChild() == this ? parent.getRightChild() : parent.getLeftChild();
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

    public List<Event> checkCircleEvents(double sweepY) {
        // l2 -> l1 -> leaf <- r1 <- r2
        Optional<LeafBeachNode> l1 = getLeftNeighbor();
        Optional<LeafBeachNode> l2 = l1.flatMap(LeafBeachNode::getLeftNeighbor);
        Optional<LeafBeachNode> r1 = getRightNeighbor();
        Optional<LeafBeachNode> r2 = r1.flatMap(LeafBeachNode::getRightNeighbor);

        val events = new LinkedList<Event>();

        if (l2.isPresent()) {
            VertexEvent.build(l2.get(), l1.get(), this).filter(e -> e.getPoint().y <= sweepY).ifPresent(events::add);
        }
        if (l1.isPresent() && r1.isPresent()) {
            VertexEvent.build(l1.get(), this, r1.get()).filter(e -> e.getPoint().y <= sweepY).ifPresent(events::add);
        }
        if (r2.isPresent()) {
            VertexEvent.build(this, r1.get(), r2.get()).filter(e -> e.getPoint().y <= sweepY).ifPresent(events::add);
        }
        return events;
    }

}