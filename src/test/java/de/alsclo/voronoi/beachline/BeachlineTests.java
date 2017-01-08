package de.alsclo.voronoi.beachline;

import de.alsclo.voronoi.graph.Point;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeachlineTests {

    @Test
    public void testInsert() {
        Beachline bl = new Beachline();
        assertNull(bl.getRoot());
        LeafBeachNode root = bl.insertArc(new Point(0,0)).newLeaf;
        assertEquals(root, bl.getRoot());
        LeafBeachNode first = bl.insertArc(new Point(0.5, 4)).newLeaf;
        assertNotEquals(root, bl.getRoot());
        assertTrue(bl.getRoot() instanceof InnerBeachNode);
        InnerBeachNode newRoot = (InnerBeachNode) bl.getRoot();
        assertEquals(newRoot.getLeftmostLeaf(), newRoot.getRightmostLeaf());
        assertEquals(newRoot.getLeftChild(), root);
        assertTrue(newRoot.getRightChild() instanceof InnerBeachNode);
        InnerBeachNode tmp = (InnerBeachNode) newRoot.getRightChild();
        assertEquals(tmp.getRightChild(), root);
        assertEquals(tmp.getLeftChild(), first);
    }

    @Test
    public void testNeightbor() {
        Beachline bl = new Beachline();
        //      O
        //     / \
        //    O   O
        //   /|   |\
        //  1 2   3 4
        val l1 = new LeafBeachNode(new Point(1, 0));
        val l2 = new LeafBeachNode(new Point(2, 0));
        val l3 = new LeafBeachNode(new Point(3, 0));
        val l4 = new LeafBeachNode(new Point(4, 0));
        bl.setRoot(new InnerBeachNode(new InnerBeachNode(l1, l2), new InnerBeachNode(l3, l4)));

        assertFalse(l1.getLeftNeighbor().isPresent());
        assertFalse(l4.getRightNeighbor().isPresent());
        assertTrue(l1.getRightNeighbor().orElse(null) == l2);
        assertTrue(l2.getRightNeighbor().orElse(null) == l3);
        assertTrue(l3.getRightNeighbor().orElse(null) == l4);
        assertTrue(l4.getLeftNeighbor().orElse(null) == l3);
        assertTrue(l3.getLeftNeighbor().orElse(null) == l2);
        assertTrue(l2.getLeftNeighbor().orElse(null) == l1);


    }

}
