package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.beachline.LeafBeachNode;
import de.alsclo.voronoi.util.Point;

import static de.alsclo.voronoi.util.Math.sq;
import static java.lang.Math.sqrt;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class VertexEvent extends Event {

    private final LeafBeachNode l;
    private final LeafBeachNode c;
    private final LeafBeachNode r;
    private final Circle circle;

    private VertexEvent(LeafBeachNode l, LeafBeachNode c, LeafBeachNode r, Circle circle) {
        super(circle.center.y - circle.radius);
        this.l = l;
        this.c = c;
        this.r = r;
        this.circle = circle;
    }

    public VertexEvent(LeafBeachNode l, LeafBeachNode c, LeafBeachNode r) {
        this(l, c, r, new Circle(l, c, r));
    }

    @Override
    public Collection<Event> handle(Beachline beachline) {
        return Collections.emptyList();
    }

    private static class Circle {
        private final Point center;
        private final double radius;

        private Circle(LeafBeachNode l, LeafBeachNode c, LeafBeachNode r) {
            double ma = (c.getSiteY() - l.getSiteY()) / (c.getSiteX() - l.getSiteX());
            double mb = (r.getSiteY() - c.getSiteY()) / (r.getSiteX() - c.getSiteX());

            double x = (ma * mb * (l.getSiteY() - r.getSiteY()) + mb * (l.getSiteX() + c.getSiteX()) + ma * (c.getSiteX() + r.getSiteX())) / (2.0 * (mb - ma));
            double y = (x - (l.getSiteX() + c.getSiteX()) / 2.0) / ma + (l.getSiteY() + c.getSiteY()) / 2.0;
            center = new Point(x, y);
            radius = sqrt(sq(c.getSiteX() - x) + sq(c.getSiteY() - y));
        }
    }
}
