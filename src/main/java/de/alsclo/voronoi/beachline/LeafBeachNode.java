package de.alsclo.voronoi.beachline;

public class LeafBeachNode extends BeachNode {

    private final double siteX;
    private final double siteY;

    public LeafBeachNode(double siteX, double siteY) {
        this.siteX = siteX;
        this.siteY = siteY;
    }

    @Override
    public BeachNode insertArc(double siteX, double siteY) {
        return null;
    }
}