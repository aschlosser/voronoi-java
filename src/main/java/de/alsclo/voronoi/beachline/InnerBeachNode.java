package de.alsclo.voronoi.beachline;

import de.alsclo.voronoi.graph.Point;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static de.alsclo.voronoi.Math.sq;

@Getter
@ToString @EqualsAndHashCode(callSuper = false)
public class InnerBeachNode extends BeachNode {

    private BeachNode leftChild;
    private BeachNode rightChild;

    InnerBeachNode() {
        
    }

    InnerBeachNode(BeachNode leftChild, BeachNode rightChild) {
        setLeftChild(leftChild);
        setRightChild(rightChild);
    }

    @Override
    public InsertionResult insertArc(Point newSite) {
        // Find leafs represented by this inner node
        Point l = leftChild.getRightmostLeaf().getSite();
        Point r = rightChild.getLeftmostLeaf().getSite();

        // Calculate the intersections between both parabolas of those leafs
        double lh = l.y - newSite.y;
        double rh = r.y - newSite.y;

        double a = 0.5 / lh - 0.5 / rh;
        double b = r.x / rh - l.x / lh;
        double c = (sq(l.x) + sq(l.y) - sq(newSite.y)) / (2.0 * lh)
                - (sq(r.x) + sq(r.y) - sq(newSite.y)) / (2.0 * rh);

        double x1 = (-b + Math.sqrt(sq(b) - 4.0 * a * c)) / 2.0 * a;
        double x2 = (-b - Math.sqrt(sq(b) - 4.0 * a * c)) / 2.0 * a;

        if (newSite.x < Math.min(x1, x2)) {
            return leftChild.insertArc(newSite);
        } else if (newSite.x > Math.max(x1, x2)) {
            return rightChild.insertArc(newSite);
        } else {
            // The new arc is between both intersection points, choose the lower leaf to be expanded
            return (l.y < r.y ? leftChild : rightChild).insertArc(newSite);
        }
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