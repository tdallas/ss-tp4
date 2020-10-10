package planets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PlanetFileGenerator {
    private final BufferedWriter bw;
    private FileWriter fw;

    public PlanetFileGenerator(String filename) {
        try {
            File directory = new File("out/");
            if (!directory.exists()) {
                directory.mkdir();
            }
            FileWriter pw = new FileWriter("out/" + filename + ".xyz");
            pw.close();
            this.fw = new FileWriter("out/" + filename + ".xyz", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.bw = new BufferedWriter(fw);
    }

    public void addToFile(List<Planet> planets, double timePassed) {
        try {
            bw.write(planets.size() + "\n");
            bw.write("id xPosition yPosition xVelocity yVelocity radius mass redColor greenColor blueColor timePassed\n");
            for (Planet planet : planets) {
                bw.write(planet.getName() + " " +
                        planet.getPosition().getX() + " " +
                        planet.getPosition().getY() + " " +
                        planet.getVelocity().getX() + " " +
                        planet.getVelocity().getY() + " " +
                        planet.getRadius() + " " +
                        planet.getMass() + " " +
                        planet.getRedColor() + " " +
                        planet.getGreenColor() + " " +
                        planet.getBlueColor() + " " +
                        timePassed + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
