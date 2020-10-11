package planets.integrators;

import planets.Planet;
import planets.PlanetVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetBeemanIntegrator extends PlanetIntegrator {
    private Map<Planet, PlanetVector> previousAccelerations;

    public PlanetBeemanIntegrator(double gravitationalConstant) {
        super(gravitationalConstant);
        previousAccelerations = null;
    }

    public void applyIntegrator(double timeDelta, Planet planet, List<Planet> planets) {
        PlanetVector forces = getForces(planet, planet.getPosition(), planets);
        if (previousAccelerations == null) {
            previousAccelerations = new HashMap<>();
            for (Planet p : planets) {
                PlanetVector previousPosition = p.getPosition().subtract(p.getVelocity().multiply(timeDelta)).add(forces.multiply((timeDelta * timeDelta) / (2 * p.getMass())));
                PlanetVector previousAcceleration = getForces(p, previousPosition, planets).divide(p.getMass());
                previousAccelerations.put(p, previousAcceleration);
            }
        }
        PlanetVector acceleration = forces.divide(planet.getMass());
        PlanetVector previousAcceleration = previousAccelerations.get(planet);
        planet.setPosition(planet.getPosition().add(planet.getVelocity().multiply(timeDelta)).add(acceleration.multiply((2.0 / 3) * timeDelta * timeDelta)).subtract(previousAcceleration.multiply((1.0 / 6) * timeDelta * timeDelta)));
        //predict velocity with position
        PlanetVector nextAcceleration = getForces(planet, planet.getPosition(), planets).divide(planet.getMass());
        //correct velocity
        planet.setVelocity(planet.getVelocity().add(nextAcceleration.multiply((1.0 / 3) * timeDelta)).add(acceleration.multiply((5.0 / 6) * timeDelta)).subtract(previousAcceleration.multiply((1.0 / 6) * timeDelta)));
        previousAccelerations.replace(planet, acceleration);
    }
}
