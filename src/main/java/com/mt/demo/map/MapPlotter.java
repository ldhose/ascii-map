package com.mt.demo.map;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class MapPlotter {


    private Scale scale = Scale.NORMAL;


    public void drawMap(Set<Point<Float>> pointsToPlot, Scale scale) throws IOException {

        Set<Point<Integer>> points = pointsToPlot.stream()
                    .map(this::convertFloatPointToInteger)
                    .collect(Collectors.toSet());

        DrawMap drawMap = new DrawMap(points);
        drawMap.draw();

    }

    private Point<Integer> convertFloatPointToInteger(Point<Float> floatPoint) {
        Float x = floatPoint.x() * Double.valueOf(Math.pow(10,scale.ordinal())).floatValue();
        Float y = floatPoint.y() * Double.valueOf(Math.pow(10,scale.ordinal())).floatValue();
        return new Point<Integer>(x.intValue(), y.intValue());
    }

}
