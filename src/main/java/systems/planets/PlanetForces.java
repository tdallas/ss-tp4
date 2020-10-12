package systems.planets;

import engine.Particle;
import engine.Vector;
import engine.integrators.Forces;

import java.util.List;

public class PlanetForces implements Forces {
    private final double gravitationalConstant;

    public PlanetForces(double gravitationalConstant) {
        this.gravitationalConstant = gravitationalConstant;
    }

    @Override
    public Vector getForces(Particle particle, Vector position, Vector velocity, List<Particle> particles) {
        Vector forces = new Vector(0, 0);

        for (Particle p : particles) {
            if (!particle.equals(p)) {
                double distance = position.distance(p.getPosition());
                double gravitationalForce = gravitationalConstant * particle.getMass() * p.getMass() / (distance * distance);
                forces = forces.add(p.getPosition().subtract(position).divide(distance).multiply(gravitationalForce));
            }
        }

        return forces;
    }
}
