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
        double xForces = 0;
        double yForces = 0;

        for (Planet aux : planets) {
            if (!planet.equals(aux)) {
                double f = gravitationalConstant * planet.getMass() * aux.getMass() / position.distanceSquare(aux.getPosition());

                PlanetVector e = aux.getPosition().sub(position).mul(1 / position.distance(aux.getPosition()));
                PlanetVector projectedForces = e.mul(f);
                xForces += projectedForces.getX();
                yForces += projectedForces.getY();
            }
        }

        return new PlanetVector(xForces, yForces);
    }
}
