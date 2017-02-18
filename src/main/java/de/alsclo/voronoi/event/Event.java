package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.graph.Graph;
import de.alsclo.voronoi.graph.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public abstract class Event implements Comparable<Event> {

    @Getter
    private final Point point;

    @Override
    public int compareTo(Event o) {
        return Double.compare(o.point.y, point.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return point.equals(event.point);
    }

    @Override
    public int hashCode() {
        return point.hashCode();
    }

    public abstract void handle(Collection<Event> eventQueue, Beachline beachline, Graph graph);


}
