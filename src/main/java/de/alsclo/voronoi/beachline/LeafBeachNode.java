package de.alsclo.voronoi.beachline;

import de.alsclo.voronoi.graph.Point;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = false)
public class LeafBeachNode extends BeachNode {

    private final Point site;

    private LeafBeachNode(LeafBeachNode other) {
        this(other.site);
    }

    public LeafBeachNode(Point site) {
        this.site = site;
    }

    @Override
    public InsertionResult insertArc(Point newSite) {
        val replacement = new InnerBeachNode();
        val newLeaf = new LeafBeachNode(newSite);
        val median = new InnerBeachNode();
        if (newSite.x < site.x) {
            median.setLeftChild(new LeafBeachNode(this));
            median.setRightChild(newLeaf);
            replacement.setLeftChild(median);
            replacement.setRightChild(new LeafBeachNode(this));
        } else {
            replacement.setLeftChild(new LeafBeachNode(this));
            median.setLeftChild(newLeaf);
            median.setRightChild(new LeafBeachNode(this));
            replacement.setRightChild(median);
        }
        if (getParent().getLeftChild() == this) {
            getParent().setLeftChild(replacement);
        } else {
            getParent().setRightChild(replacement);
        }
        return new InsertionResult(Optional.of(this), newLeaf);
    }

    @Override
    public LeafBeachNode getLeftmostLeaf() {
        return this;
    }

    @Override
    public LeafBeachNode getRightmostLeaf() {
        return this;
    }

    public Optional<LeafBeachNode> getLeftNeighbor() {
        InnerBeachNode current = getParent();
        BeachNode child = this;
        while(current.getParent() != null) {
            if (current.getRightChild() == child) {
                return Optional.of(current.getLeftChild().getRightmostLeaf());
            } else {
                child = current;
                current = current.getParent();
            }
        }
        return Optional.empty();
    }

    public Optional<LeafBeachNode> getRightNeighbor() {
        InnerBeachNode current = getParent();
        BeachNode child = this;
        while(current.getParent() != null) {
            if (current.getLeftChild() == child) {
                return Optional.of(current.getRightChild().getLeftmostLeaf());
            } else {
                child = current;
                current = current.getParent();
            }
        }
        return Optional.empty();
    }


}