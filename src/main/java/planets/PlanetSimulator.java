package planets;

import planets.cutCondition.PlanetCutCondition;
import planets.integrators.PlanetIntegrator;

import java.util.List;

public class PlanetSimulator {
    private final double timeDelta;
    private final double saveTimeDelta;
    private final PlanetCutCondition planetCutCondition;
    private final PlanetIntegrator planetIntegrator;
    private final PlanetFileGenerator planetFileGenerator;
    private final List<Planet> planets;
    private double time;
    private double timeToSave;

    public PlanetSimulator(double timeDelta, double saveTimeDelta, PlanetCutCondition planetCutCondition, PlanetIntegrator planetIntegrator, PlanetFileGenerator planetFileGenerator, List<Planet> planets) {
        this.timeDelta = timeDelta;
        this.saveTimeDelta = saveTimeDelta;
        this.planetCutCondition = planetCutCondition;
        this.planetIntegrator = planetIntegrator;
        this.planetFileGenerator = planetFileGenerator;
        this.planets = planets;
        this.timeToSave = saveTimeDelta;
        this.time = 0;
    }

    public void simulate(boolean closeFile) {
        planetFileGenerator.addToFile(planets, time);
        while (!planetCutCondition.isFinished(planets, time)) {
            for (Planet planet : planets) {
                if(planet.getId() != 0) {
                    planetIntegrator.applyIntegrator(timeDelta, planet, planets);
                }
            }
            time += timeDelta;

            if (time >= timeToSave) {
                planetFileGenerator.addToFile(planets, time);
                timeToSave += saveTimeDelta;
            }
        }
        if(closeFile) {
            planetFileGenerator.closeFile();
        }
    }
}
