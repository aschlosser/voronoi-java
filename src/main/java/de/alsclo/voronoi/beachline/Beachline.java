package de.alsclo.voronoi.beachline;


public class Beachline {

    private BeachNode root = null;

    public BeachNode insertArc(double siteX, double siteY) {
        if (root != null) {
            return root.insertArc(siteX, siteY);
        } else {
            return root = new LeafBeachNode(siteX, siteY);
        }
    }
}
