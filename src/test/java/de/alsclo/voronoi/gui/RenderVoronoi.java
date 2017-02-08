package de.alsclo.voronoi.gui;

import de.alsclo.voronoi.Voronoi;
import de.alsclo.voronoi.graph.Edge;
import de.alsclo.voronoi.graph.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RenderVoronoi extends JFrame{

    private static final int size = 512;

    private static final int POINT_SIZE = 5;
    private final Voronoi diagram;

    public RenderVoronoi(Voronoi diagram) {
        this.diagram = diagram;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Point site : diagram.getGraph().getSitePoints()) {
            g2.fillOval((int) Math.round(site.x-POINT_SIZE/2), (int) Math.round(site.y-POINT_SIZE/2), POINT_SIZE, POINT_SIZE);
        }

        diagram.getGraph().edgeStream().filter(e -> e.getA() != null && e.getB() != null).forEach(e -> {
            Point a = e.getA().getLocation();
            Point b = e.getB().getLocation();
            g2.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
        });
    }

    public static void main(String[] args) {
        Random r = new Random(9235563856L);
        Stream<Point> gen = Stream.generate(() -> new Point(r.nextDouble() * size, r.nextDouble() * size));
        Voronoi diagram = new Voronoi(gen.limit(size).collect(Collectors.toList()));
        diagram.getGraph().edgeStream().forEach(System.out::println);
        RenderVoronoi frame = new RenderVoronoi(diagram);
        frame.setSize(size, size);
        frame.setVisible(true);
    }
}
