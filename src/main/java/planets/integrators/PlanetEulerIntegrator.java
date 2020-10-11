package planets.integrators;

import planets.Planet;
import planets.PlanetVector;

import java.util.List;

public class PlanetEulerIntegrator extends PlanetIntegrator {
    public PlanetEulerIntegrator(double gravitationalConstant) {
        super(gravitationalConstant);
    }

    @Override
    public void applyIntegrator(double timeDelta, Planet planet, List<Planet> planets) {
        PlanetVector forces = getForces(planet, planet.getPosition(), planets);
        planet.setVelocity(planet.getVelocity().add(forces.multiply(timeDelta / planet.getMass())));
        planet.setPosition(planet.getPosition().add(planet.getVelocity().multiply(timeDelta)).add(forces.multiply(timeDelta * timeDelta / (2 * planet.getMass()))));
    }
}
