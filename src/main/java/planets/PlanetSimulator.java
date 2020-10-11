package planets;

import planets.cutCondition.PlanetCutCondition;
import planets.integrators.PlanetIntegrator;

import java.util.List;

public class PlanetSimulator {
    private static final double SPACESHIP_MASS = 5 * Math.pow(10, 5);                    // kg
    private static final double SPACESHIP_RADIUS = 50;                                   // m
    private static final double SPACESHIP_HEIGHT = 1500 * Math.pow(10, 3);               // m
    private static final double SPACESHIP_ORBITAL_VELOCITY = 7.12 * Math.pow(10, 3);     // m/s
    private static final double SPACESHIP_LAUNCH_SPEED = 8 * Math.pow(10, 3);            // m/s

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

    private void addSpaceship(List<Planet> planets){
        Planet sun = planets.get(0);
        Planet earth = planets.get(1);
        double angle = Math.atan2(earth.getPosition().getX() - sun.getPosition().getX(), earth.getPosition().getY() - sun.getPosition().getY());
        double distanceToEarthCenter = SPACESHIP_HEIGHT + earth.getRadius();
        double xpos = earth.getPosition().getX() + Math.cos(angle) * distanceToEarthCenter;
        double ypos = earth.getPosition().getY() + Math.sin(angle) * distanceToEarthCenter;
        double vx = earth.getVelocity().getX() + Math.cos(angle) * (SPACESHIP_LAUNCH_SPEED + SPACESHIP_ORBITAL_VELOCITY);
        double vy = earth.getVelocity().getY() + Math.sin(angle) * (SPACESHIP_LAUNCH_SPEED + SPACESHIP_ORBITAL_VELOCITY);
        double radius = SPACESHIP_RADIUS;
        double mass = SPACESHIP_MASS;
        planets.add(new Planet(3,
                new PlanetVector(xpos, ypos),
                new PlanetVector(vx, vy),
                mass, radius,
                0, 0, 1,
                radius * 50000000));
    }
}
