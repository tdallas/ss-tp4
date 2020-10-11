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

        for (Planet aux : planets) {
            if (!planet.equals(aux)) {
                double gravitationalForce = gravitationalConstant * planet.getMass() * aux.getMass() / Math.pow(position.distance(aux.getPosition()), 2);
                forces = forces.add(aux.getPosition().subtract(position).multiply(1 / position.distance(aux.getPosition())).multiply(gravitationalForce));
            }
        }

        return forces;
    }
}
