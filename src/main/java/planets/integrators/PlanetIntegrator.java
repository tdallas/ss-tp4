package planets.integrators;

import planets.Planet;
import planets.PlanetVector;

import java.util.List;

public abstract class PlanetIntegrator {

    private final double gravitationalConstant;

    public PlanetIntegrator(double gravitationalConstant) {
        this.gravitationalConstant = gravitationalConstant;
    }

    public abstract void applyIntegrator(double timeDelta, Planet planet, List<Planet> planets);

    PlanetVector getForces(Planet planet, PlanetVector position, List<Planet> planets) {
        PlanetVector forces = new PlanetVector(0, 0);

        for (Planet p : planets) {
            if (!planet.equals(p)) {
                double gravitationalForce = gravitationalConstant * planet.getMass() * p.getMass() / Math.pow(position.distance(p.getPosition()), 2);
                forces = forces.add(p.getPosition().subtract(position).multiply(1 / position.distance(p.getPosition())).multiply(gravitationalForce));
            }
        }

        return forces;
    }
}
