package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.beachline.LeafBeachNode;
import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import de.alsclo.voronoi.graph.Vertex;
import lombok.val;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static de.alsclo.voronoi.Math.sq;
import static java.lang.Math.sqrt;

public class VertexEvent extends Event {

    private final LeafBeachNode l;
    private final LeafBeachNode c;
    private final LeafBeachNode r;
    private final Circle circle;

    private VertexEvent(LeafBeachNode l, LeafBeachNode c, LeafBeachNode r, Circle circle) {
        super(new Point(circle.center.x, circle.center.y - circle.radius));
        this.l = l;
        this.c = c;
        this.r = r;
        this.circle = circle;
    }

    public static Optional<VertexEvent> build(LeafBeachNode l, LeafBeachNode c, LeafBeachNode r) {
        Circle circle = new Circle(l.getSite(), c.getSite(), r.getSite());
        if (circle.isValid()) {
            return Optional.of(new VertexEvent(l,c,r,circle));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Event> handle(Beachline beachline, Graph graph) {
        // If l,c,r changed since the inception of this event we have to discard it
        if (!c.getLeftNeighbor().filter(l::equals).isPresent() || !c.getRightNeighbor().filter(r::equals).isPresent()) {
            return Collections.emptyList();
        }

        c.remove();

        Vertex v = new Vertex(circle.center);
        graph.addVertex(v);
        graph.getEdgeBetweenSites(l.getSite(), c.getSite()).ifPresent(e -> e.addVertex(v));
        graph.getEdgeBetweenSites(r.getSite(), c.getSite()).ifPresent(e -> e.addVertex(v));
        Edge e = new Edge(l.getSite(), r.getSite());
        graph.addEdge(e);
        e.addVertex(v);

        return Collections.emptyList();
    }

    private static class Circle {
        private final Point center;
        private final double radius;

        private Circle(Point l, Point c, Point r) {
            if (l.x != c.x && c.x != r.x) {
                center = computeCenter(l, c, r);
            } else if (c.x != l.x && r.x != l.x) {
                center = computeCenter(c, l, r);
            } else if (c.x != r.x && l.x != r.x) {
                center = computeCenter(c, r, l);
            } else {
                center = new Point(Double.NaN, Double.NaN);
            }
            radius = sqrt(sq(c.x - center.x) + sq(c.y - center.y));
        }

        private static Point computeCenter(Point l, Point c, Point r) {
            double ma = (c.y - l.y) / (c.x - l.x);
            double mb = (r.y - c.y) / (r.x - c.x);

            double x = (ma * mb * (l.y - r.y) + mb * (l.x + c.x) - ma * (c.x + r.x)) / (2.0 * (mb - ma));
            if (ma != 0.0) {
                double y = -(x - (l.x + c.x) / 2.0) / ma + (l.y + c.y) / 2.0;
                return new Point(x, y);
            } else {
                double y = -(x - (c.x + r.x) / 2.0) / mb + (c.y + r.y) / 2.0;
                return new Point(x, y);
            }
        }

        public boolean isValid() {
            return Double.isFinite(radius);
        }
    }
}
