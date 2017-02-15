package de.alsclo.voronoi.gui;

import de.alsclo.voronoi.Voronoi;
import de.alsclo.voronoi.graph.Point;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RenderVoronoi extends JFrame{

    private static final int size = 512;

    private static final double POINT_SIZE = 5.0;
    private final Voronoi diagram;

    public RenderVoronoi(Voronoi diagram) {
        this.diagram = diagram;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Point site : diagram.getGraph().getSitePoints()) {
            g2.fillOval((int) Math.round(site.x-POINT_SIZE/2), size - (int) Math.round(site.y-POINT_SIZE/2) + 32, (int)POINT_SIZE, (int)POINT_SIZE);
            g2.drawString(String.format("%d,%d", (int)site.x, (int)site.y), (int) site.x, size - (int)site.y + 32);
        }

        diagram.getGraph().edgeStream().filter(e -> e.getA() != null && e.getB() != null).forEach(e -> {
            Point a = e.getA().getLocation();
            Point b = e.getB().getLocation();
            g2.drawLine((int)a.x, size -(int)a.y, (int)b.x, size -(int)b.y);
        });
    }

    public static void main(String[] args) {
        Random r = new Random(9235563856L);
        Stream<Point> gen = Stream.generate(() -> new Point(r.nextDouble() * size, r.nextDouble() * size));
        Voronoi diagram = new Voronoi(gen.limit(10).collect(Collectors.toList()));
//        val p1 = new Point(0.5 * size / 2, 1.5 * size / 2);
//        val p2 = new Point(1.6 * size / 2, 1.5 * size / 2);
//        val p3 = new Point(1.6 * size / 2, 0.5 * size / 2);
//        val p4 = new Point(0.5 * size / 2, 0.5 * size / 2);
//
//        val c = (new Point(1.11 * size / 2, 1 * size / 2));
//
//        val points = Arrays.asList(c, p1, p2, p3, p4);
//        Voronoi diagram = new Voronoi(points);
        diagram.getGraph().edgeStream().forEach(System.out::println);
        RenderVoronoi frame = new RenderVoronoi(diagram);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(size, size+30);
        frame.setVisible(true);
    }
}
