package planets.integrators;

import planets.Planet;
import planets.PlanetSimulator;
import planets.Vector;

import java.util.List;

public abstract class PlanetIntegrator {

    public abstract void applyIntegrator(double timeDelta, Planet planet, List<Planet> planets);

    Vector getForces(Planet planet, Vector position, List<Planet> planets) {
        double xForces = 0;
        double yForces = 0;

        for (Planet aux: planets) {
            if(!planet.equals(aux)) {
                double f = PlanetSimulator.GRAVITATIONAL_CONSTANT * planet.getMass() * aux.getMass() / position.distanceSquare(aux.getPosition());

                Vector e = aux.getPosition().sub(position).mul(1 / position.distance(aux.getPosition()));
                Vector projectedForces = e.mul(f);
                xForces += projectedForces.getX();
                yForces += projectedForces.getY();
            }
        }

        return new Vector(xForces, yForces);
    }
}
