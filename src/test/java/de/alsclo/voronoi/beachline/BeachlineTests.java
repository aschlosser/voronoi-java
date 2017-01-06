package de.alsclo.voronoi.beachline;

import org.junit.Test;

import static org.junit.Assert.*;

public class BeachlineTests {

    @Test
    public void testInsert() {
        Beachline bl = new Beachline();
        assertNull(bl.getRoot());
        LeafBeachNode root = bl.insertArc(0, 0);
        assertEquals(root, bl.getRoot());
        LeafBeachNode first = bl.insertArc(0.5, 4);
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

}
