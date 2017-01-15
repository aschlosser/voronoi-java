package de.alsclo.voronoi.beachline;


import de.alsclo.voronoi.graph.Point;

import java.util.Optional;
import java.util.stream.Stream;

public class Beachline {

    private final InnerBeachNode rootContainer = new InnerBeachNode();

    public InsertionResult insertArc(Point newSite) {
        BeachNode root = getRoot();
        if (root != null) {
            return root.insertArc(newSite);
        } else {
            LeafBeachNode l = new LeafBeachNode(newSite);
            setRoot(l);
            return new InsertionResult(Optional.empty(), l);
        }
    }

    BeachNode getRoot() {
        return rootContainer.getLeftChild();
    }

    void setRoot(BeachNode n) {
        rootContainer.setLeftChild(n);
    }

    public Stream<LeafBeachNode> leafIterator() {
        return getRoot().leafIterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Beachline(");
        if (getRoot() != null) {
            leafIterator().forEachOrdered(l -> sb.append(l.getSite()).append(","));
        }
        sb.append(")");
        return sb.toString();
    }
}
