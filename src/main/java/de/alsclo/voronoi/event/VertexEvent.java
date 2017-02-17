package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.beachline.LeafBeachNode;
import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import de.alsclo.voronoi.graph.Vertex;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static de.alsclo.voronoi.Math.EPSILON;
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
        l.subscribe(this);
        c.subscribe(this);
        r.subscribe(this);
    }

    public static Optional<VertexEvent> build(LeafBeachNode l, LeafBeachNode c, LeafBeachNode r) {
        Point ap = l.getSite(), bp = c.getSite(), cp = r.getSite();
        double convergence = (ap.y - bp.y) * (bp.x - cp.x) - (bp.y - cp.y) * (ap.x - bp.x);
        if (convergence > 0) {
            Circle circle = new Circle(ap, bp, cp);
            if (circle.isValid()) {
                return Optional.of(new VertexEvent(l, c, r, circle));
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Event> handle(Collection eventQueue, Beachline beachline, Graph graph) {
        assert c.getLeftNeighbor().filter(l::equals).isPresent();
        assert c.getRightNeighbor().filter(r::equals).isPresent();
        assert l.getRightNeighbor().filter(c::equals).isPresent();
        assert r.getLeftNeighbor().filter(c::equals).isPresent();

        if (graph.getSitePoints().stream().anyMatch(circle::contains)) {
            return Collections.emptyList();
        }

        c.remove();
        c.getSubscribers().forEach(e -> {
            eventQueue.remove(e);
            c.unsubscribe(e);
        });

        Vertex v = new Vertex(circle.center);
        graph.addVertex(v);
        assert graph.getEdgeBetweenSites(l.getSite(), c.getSite()).isPresent();
        assert graph.getEdgeBetweenSites(r.getSite(), c.getSite()).isPresent();
        graph.getEdgeBetweenSites(l.getSite(), c.getSite()).ifPresent(e -> e.addVertex(v));
        graph.getEdgeBetweenSites(r.getSite(), c.getSite()).ifPresent(e -> e.addVertex(v));
        Edge e = new Edge(l.getSite(), r.getSite());
        graph.addEdge(e);
        e.addVertex(v);

        List<Event> events = l.checkCircleEvents(getPoint().y);
        events.addAll(r.checkCircleEvents(getPoint().y));
        return events;
    }

    @ToString
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

        public boolean contains(Point p) {
            return sqrt(sq(p.x - center.x) + sq(p.y - center.y)) < (radius - EPSILON);
        }
    }
}
