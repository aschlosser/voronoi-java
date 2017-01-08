package de.alsclo.voronoi.beachline;

import de.alsclo.voronoi.graph.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BeachNode {

    private InnerBeachNode parent;

    public abstract InsertionResult insertArc(Point newSite);

    public abstract LeafBeachNode getLeftmostLeaf();
    public abstract LeafBeachNode getRightmostLeaf();
}
