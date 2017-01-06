package de.alsclo.voronoi.beachline;

import lombok.Data;

@Data
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
        InnerBeachNode replacement = new InnerBeachNode();
        LeafBeachNode newLeaf = new LeafBeachNode(newSiteX, newSiteY);
        if (newSiteX < siteX) {
            InnerBeachNode tmp = new InnerBeachNode();
            tmp.setLeftChild(new LeafBeachNode(this));
            tmp.setRightChild(newLeaf);
            replacement.setLeftChild(tmp);
            replacement.setRightChild(new LeafBeachNode(this));
        } else {
            replacement.setLeftChild(new LeafBeachNode(this));
            InnerBeachNode tmp = new InnerBeachNode();
            tmp.setLeftChild(newLeaf);
            tmp.setRightChild(new LeafBeachNode(this));
            replacement.setRightChild(tmp);
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