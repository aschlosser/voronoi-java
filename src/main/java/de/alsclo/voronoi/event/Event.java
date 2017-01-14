package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public abstract class Event implements Comparable<Event>{

    @Getter
    private final Point point;

    @Override
    public int compareTo(Event o) {
        int tmp = Double.compare(o.point.y, point.y);
        return tmp == 0 ? Double.compare(point.x, o.point.x) : tmp;
    }

    public abstract Collection<Event> handle(Beachline beachline, Graph graph);
}
