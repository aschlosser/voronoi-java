package de.alsclo.voronoi.beachline;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
public class InnerBeachNode extends BeachNode {

    private BeachNode leftChild;
    private BeachNode rightChild;

    @Override
    public LeafBeachNode insertArc(double siteX, double siteY) {
        // Find leafs represented by this inner node
        LeafBeachNode l = leftChild.getRightmostLeaf();
        LeafBeachNode r = rightChild.getLeftmostLeaf();

        // Calculate the intersections between both parabolas of those leafs
        double lh = l.getSiteY() - siteY;
        double rh = r.getSiteY() - siteY;

        double a = 0.5 / lh - 0.5 / rh;
        double b = r.getSiteX() / rh - l.getSiteX() / lh;
        double c = (sq(l.getSiteX()) + sq(l.getSiteY()) - sq(siteY)) / (2.0 * lh)
                - (sq(r.getSiteX()) + sq(r.getSiteY()) - sq(siteY)) / (2.0 * rh);

        double x1 = (-b + Math.sqrt(sq(b) - 4.0 * a * c)) / 2.0 * a;
        double x2 = (-b - Math.sqrt(sq(b) - 4.0 * a * c)) / 2.0 * a;

        if (siteX < Math.min(x1, x2)) {
            return leftChild.insertArc(siteX, siteY);
        } else if (siteX > Math.max(x1, x2)) {
            return rightChild.insertArc(siteX, siteY);
        } else {
            // The new arc is between both intersection points, choose the lower leaf to be expanded
            return (l.getSiteY() < r.getSiteY() ? leftChild : rightChild).insertArc(siteX, siteY);
        }
    }

    private static double sq(double o) {
        return o * o;
    }

    @Override
    public LeafBeachNode getLeftmostLeaf() {
        return leftChild.getLeftmostLeaf();
    }

    @Override
    public LeafBeachNode getRightmostLeaf() {
        return rightChild.getRightmostLeaf();
    }


    public void setLeftChild(BeachNode leftChild) {
        this.leftChild = leftChild;
        leftChild.setParent(this);
    }

    public void setRightChild(BeachNode rightChild) {
        this.rightChild = rightChild;
        rightChild.setParent(this);
    }

}