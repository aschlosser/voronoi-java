package de.alsclo.voronoi.beachline;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class InsertionResult {
    public final Optional<LeafBeachNode> splitLeaf;
    public final LeafBeachNode newLeaf;
}
