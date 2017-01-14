package de.alsclo.voronoi.graph;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class BisectorMap {

    private final Map<Bisector, Edge> data = new HashMap<>();

    public void put(@NonNull Point a, @NonNull Point b, @NonNull Edge e) {
        assert !data.containsKey(new Bisector(a, b));
        data.put(new Bisector(a, b), e);
    }

    public Edge get(@NonNull Point a, @NonNull Point b) {
        return data.get(new Bisector(a, b));
    }

    public Collection<Edge> values() {
        return data.values();
    }

    @RequiredArgsConstructor @ToString
    private static class Bisector {
        @NonNull
        private final Point a, b;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Bisector bisector = (Bisector) o;
            return (a.equals(bisector.a) && b.equals(bisector.b)) || (a.equals(bisector.b) && b.equals(bisector.a));
        }

        @Override
        public int hashCode() {
            return a.hashCode() + b.hashCode();
        }
    }
}
