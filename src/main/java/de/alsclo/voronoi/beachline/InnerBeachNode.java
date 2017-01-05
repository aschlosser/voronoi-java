package de.alsclo.voronoi.beachline;

import de.alsclo.voronoi.Point;
import lombok.Data;

@Data
public class InnerBeachNode extends BeachNode {

    private BeachNode leftChild;
    private BeachNode rightChild;

    private Point leftSite;
    private Point rightSite;

    @Override
    public BeachNode insertArc(double siteX, double siteY) {
        return null;
    }

}