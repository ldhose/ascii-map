package com.mt.demo.map;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

class DrawMap {

    private Integer minX = Integer.MAX_VALUE;
    private Integer minY = Integer.MAX_VALUE;
    private Integer maxX = Integer.MIN_VALUE;
    private Integer maxY = Integer.MIN_VALUE;


    private Set<Point<Integer>> points;

    public DrawMap(Set<Point<Integer>> points) {
        this.points = points;
    }

    public void draw() throws IOException {
        findMinMaxForPoints();
        movePointsToOrigin();
        plotPoints();

    }

    private void movePointsToOrigin() {
        points = points.stream()
                .map(p -> new Point<>(p.x() - minX, p.y() - minY))
                .collect(Collectors.toSet());
    }


    private void plotPoints() throws IOException {
        boolean[][] mapMatrix = new boolean[1 + maxX - minX][1 + maxY - minY];
        points.stream()

                .forEach(point -> mapMatrix[point.x()][point.y()] = true);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html> <body><p>");
        for (int i = 0; i < mapMatrix.length; i++) {
            for (int j = 0; j < mapMatrix[i].length; j++) {
                if (mapMatrix[i][j])
                    stringBuilder.append("e");
                else
                    stringBuilder.append("&nbsp");
            }
            stringBuilder.append("<br>");
        }
        stringBuilder.append("</p> </body></html>");
        File file = new File("output.html");
        System.out.println("Output html file location " + file.getAbsolutePath());
        FileUtils.write(file, stringBuilder.toString(), StandardCharsets.UTF_8);
    }

    private void findMinMaxForPoints() {

        for (Point<Integer> p : points) {
            if (p.x() > maxX) {
                maxX = p.x();

            }
            if (p.x() < minX) {
                minX = p.x();
            }
            if (p.y() > maxY)
                maxY = p.y();
            if (p.y() < minY)
                minY = p.y();
        }
    }

}
