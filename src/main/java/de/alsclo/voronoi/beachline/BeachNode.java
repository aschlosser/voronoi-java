package de.alsclo.voronoi.beachline;

import lombok.Data;

@Data
public abstract class BeachNode {

    private InnerBeachNode parent;

    public abstract LeafBeachNode insertArc(double siteX, double siteY);

    public abstract LeafBeachNode getLeftmostLeaf();
    public abstract LeafBeachNode getRightmostLeaf();
}
