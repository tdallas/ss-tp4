package engine.integrators;

import engine.Particle;
import engine.Vector;

import java.util.List;

public abstract class Integrator {
    private final Forces forces;

    public Integrator(Forces forces) {
        this.forces = forces;
    }

    public Vector getForces(Particle particle, Vector position, Vector velocity, List<Particle> particles) {
        return forces.getForces(particle, position, velocity, particles);
    }

    public abstract void applyIntegrator(double timeDelta, Particle particle, List<Particle> particles);
}
