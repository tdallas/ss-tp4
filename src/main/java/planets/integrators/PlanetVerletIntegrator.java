package planets.integrators;

import planets.Planet;
import planets.Vector;

import java.util.List;

public class PlanetVerletIntegrator extends PlanetIntegrator{
    @Override
    public void applyIntegrator(double timeDelta, Planet planet, List<Planet> planets) {
        Vector forces = getForces(planet, planet.getPosition(), planets);
        planet.setVelocity(planet.getVelocity().add(forces.mul(timeDelta/planet.getMass())));
        planet.setPosition(planet.getPosition().add(planet.getVelocity().mul(timeDelta)).add(forces.mul(timeDelta*timeDelta/(2*planet.getMass()))));
    }
}
