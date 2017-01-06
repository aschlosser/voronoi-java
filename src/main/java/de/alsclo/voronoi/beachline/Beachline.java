package de.alsclo.voronoi.beachline;


public class Beachline {

    private InnerBeachNode rootContainer = new InnerBeachNode();

    public LeafBeachNode insertArc(double siteX, double siteY) {
        BeachNode root = rootContainer.getLeftChild();
        if (root != null) {
            return root.insertArc(siteX, siteY);
        } else {
            LeafBeachNode l = new LeafBeachNode(siteX, siteY);
            rootContainer.setLeftChild(l);
            return l;
        }
    }

    BeachNode getRoot() {
        return rootContainer.getLeftChild();
    }
}
