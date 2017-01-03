package de.alsclo.voronoi;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class VoronoiTest {

    @Test
    public void test() {
        Voronoi diagram = new Voronoi();
        assertNotNull(diagram);
    }

}
