package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import de.alsclo.voronoi.graph.Graph;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public abstract class Event implements Comparable<Event>{

    @Getter
    private final double y;

    @Override
    public int compareTo(Event o) {
        return y > o.y ? -1 : 1;
    }

    public abstract Collection<Event> handle(Beachline beachline, Graph graph);
}
