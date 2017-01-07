package de.alsclo.voronoi.event;

import de.alsclo.voronoi.beachline.Beachline;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class Event implements Comparable<Event>{

    private final double y;

    @Override
    public int compareTo(Event o) {
        return y < o.y ? -1 : 1;
    }

    public abstract Optional<Event> handle(Beachline beachline);
}
