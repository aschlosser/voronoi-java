package de.alsclo.voronoi.beachline;

import lombok.Data;

@Data
public abstract class BeachNode {

    private InnerBeachNode parent;

    public abstract BeachNode insertArc(double siteX, double siteY);
}
