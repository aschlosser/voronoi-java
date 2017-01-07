package de.alsclo.voronoi;

import de.alsclo.voronoi.util.Point;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class VoronoiTest {

    @Test
    public void test() {
        val points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));

        Voronoi diagram = new Voronoi(3, 3, points);
        assertNotNull(diagram);
    }

    @Test
    public void testRejection() {
        val points = new ArrayList<Point>();
        points.add(new Point(10, 0));
        points.add(new Point(0, 10));

        try{
            new Voronoi(3, 3, points);
            fail();
        } catch (IllegalArgumentException ignored) {

        }
    }

}
