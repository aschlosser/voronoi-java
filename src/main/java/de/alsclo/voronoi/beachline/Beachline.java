package de.alsclo.voronoi.beachline;


import de.alsclo.voronoi.graph.Point;

import java.util.Optional;

public class Beachline {

    private final InnerBeachNode rootContainer = new InnerBeachNode();

    public InsertionResult insertArc(Point newSite) {
        BeachNode root = getRoot();
        if (root != null) {
            return root.insertArc(newSite);
        } else {
            LeafBeachNode l = new LeafBeachNode(newSite);
            setRoot(l);
            return new InsertionResult(Optional.empty(), l);
        }
    }

    BeachNode getRoot() {
        return rootContainer.getLeftChild();
    }

    void setRoot(BeachNode n) {
        rootContainer.setLeftChild(n);
    }
}
