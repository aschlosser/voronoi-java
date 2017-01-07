package de.alsclo.voronoi;

import de.alsclo.voronoi.util.Point;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;


public class VoronoiTest {

    @Test
    public void test() {
        Voronoi diagram = new Voronoi(10, 10, new ArrayList<Point>());
        assertNotNull(diagram);
    }

}
