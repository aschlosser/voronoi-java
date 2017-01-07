package de.alsclo.voronoi.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Edge {
    @Getter
    private final Point site1, site2;
}
