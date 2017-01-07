package de.alsclo.voronoi.beachline;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

@Data
@EqualsAndHashCode(callSuper = false)
public class LeafBeachNode extends BeachNode {

    private final double siteX;
    private final double siteY;

    private LeafBeachNode(LeafBeachNode other) {
        this(other.siteX, other.siteY);
    }

    public LeafBeachNode(double siteX, double siteY) {
        this.siteX = siteX;
        this.siteY = siteY;
    }

    @Override
    public LeafBeachNode insertArc(double newSiteX, double newSiteY) {
        val replacement = new InnerBeachNode();
        val newLeaf = new LeafBeachNode(newSiteX, newSiteY);
        val median = new InnerBeachNode();
        if (newSiteX < siteX) {
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
        return newLeaf;
    }

    @Override
    public LeafBeachNode getLeftmostLeaf() {
        return this;
    }

    @Override
    public LeafBeachNode getRightmostLeaf() {
        return this;
    }
}