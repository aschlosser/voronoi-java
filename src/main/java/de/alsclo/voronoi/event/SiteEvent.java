package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.util.Point;

import java.util.Optional;

public class SiteEvent extends Event {

    private final Point sitePoint;

    public SiteEvent(Point sitePoint) {
        super(sitePoint.y);
        this.sitePoint = sitePoint;
    }

    @Override
    public Optional<Event> handle(Beachline beachline) {
        beachline.insertArc(sitePoint.x, sitePoint.y);
        return Optional.empty();
    }
}
