package de.alsclo.voronoi.beachline;


import java.util.Optional;

public class Beachline {

    private final InnerBeachNode rootContainer = new InnerBeachNode();

    public InsertionResult insertArc(double siteX, double siteY) {
        BeachNode root = getRoot();
        if (root != null) {
            return root.insertArc(siteX, siteY);
        } else {
            LeafBeachNode l = new LeafBeachNode(siteX, siteY);
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
