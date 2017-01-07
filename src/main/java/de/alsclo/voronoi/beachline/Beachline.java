package de.alsclo.voronoi.beachline;


public class Beachline {

    private final InnerBeachNode rootContainer = new InnerBeachNode();

    public LeafBeachNode insertArc(double siteX, double siteY) {
        BeachNode root = getRoot();
        if (root != null) {
            return root.insertArc(siteX, siteY);
        } else {
            LeafBeachNode l = new LeafBeachNode(siteX, siteY);
            setRoot(l);
            return l;
        }
    }

    BeachNode getRoot() {
        return rootContainer.getLeftChild();
    }

    void setRoot(BeachNode n) {
        rootContainer.setLeftChild(n);
    }
}
