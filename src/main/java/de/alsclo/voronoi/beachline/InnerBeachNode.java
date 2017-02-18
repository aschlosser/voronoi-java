package de.alsclo.voronoi.beachline;

import de.alsclo.voronoi.graph.Point;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.stream.Stream;

import static de.alsclo.voronoi.Math.sq;
import static java.lang.Math.sqrt;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class InnerBeachNode extends BeachNode {

    private BeachNode leftChild;
    private BeachNode rightChild;

    InnerBeachNode() {

    }

    public InnerBeachNode(BeachNode leftChild, BeachNode rightChild) {
        setLeftChild(leftChild);
        setRightChild(rightChild);
    }

    @Override
    public InsertionResult insertArc(Point newSite) {
        // Find leafs represented by this inner node
        Point l = leftChild.getRightmostLeaf().getSite();
        Point r = rightChild.getLeftmostLeaf().getSite();

        // Transform coordinate to local coords
        double lxOld = l.x;
        r = new Point(r.x - l.x, r.y - newSite.y);
        l = new Point(0, l.y - newSite.y);

        // Compute intersection of parabolas
        double x;
        if (Double.compare(l.y, r.y) == 0) {
            x = r.x / 2.0;
        } else if (l.y == 0.0) {
            x = l.x;
        } else if (r.y == 0.0) {
            x = r.x;
        } else {
            x = (l.y * r.x - sqrt(l.y * r.y * (sq(l.y - r.y) + sq(r.x)))) / (l.y - r.y);
        }

        x += lxOld;

        return newSite.x < x ? leftChild.insertArc(newSite) : rightChild.insertArc(newSite);
    }

    @Override
    public LeafBeachNode getLeftmostLeaf() {
        return leftChild.getLeftmostLeaf();
    }

    @Override
    public LeafBeachNode getRightmostLeaf() {
        return rightChild.getRightmostLeaf();
    }

    void setLeftChild(BeachNode leftChild) {
        this.leftChild = leftChild;
        leftChild.setParent(this);
    }

    void setRightChild(BeachNode rightChild) {
        this.rightChild = rightChild;
        rightChild.setParent(this);
    }

}