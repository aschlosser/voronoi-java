package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.beachline.LeafBeachNode;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;

import java.util.Collection;
import java.util.Collections;

import static de.alsclo.voronoi.Math.sq;
import static java.lang.Math.sqrt;

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
        this(l, c, r, new Circle(l.getSite(), c.getSite(), r.getSite()));
    }

    @Override
    public Collection<Event> handle(Beachline beachline, Graph graph) {
        return Collections.emptyList();
    }

    private static class Circle {
        private final Point center;
        private final double radius;

        private Circle(Point l, Point c, Point r) {
            double ma = (c.y - l.y) / (c.x - l.x);
            double mb = (r.y - c.y) / (r.x - c.x);

            double x = (ma * mb * (l.y - r.y) + mb * (l.x + c.x) + ma * (c.x + r.x)) / (2.0 * (mb - ma));
            double y = (x - (l.x + c.x) / 2.0) / ma + (l.y + c.y) / 2.0;
            center = new Point(x, y);
            radius = sqrt(sq(c.x - x) + sq(c.y - y));
        }
    }
}
