package com.mt.demo;

import com.mt.demo.map.MapPlotter;
import com.mt.demo.map.Point;
import com.mt.demo.map.Scale;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	MapPlotter mapPlotter;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(DemoApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		Set<Point<Float>> pointsToPlot = fromFile("/static/ukpostcodes.csv");
		mapPlotter.drawMap(pointsToPlot, Scale.NORMAL ); //change scale to change the size of the map


	}
	public Set<Point<Float>> fromFile(String fileName) throws IOException {
		Set<Point<Float>> pointsToPlot = new HashSet<>();
		InputStream inputStream = DemoApplication.class.getResourceAsStream(fileName);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = null;
			while((line = br.readLine()) != null) {

				var parts = line.split(",");
				if (!NumberUtils.isParsable(parts[2]))
					continue;
				Float xFloat =Float.valueOf(parts[2]);
				Float yFloat = Float.valueOf(parts[3]);
				pointsToPlot.add(new Point(xFloat, yFloat));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pointsToPlot;
	}

}
