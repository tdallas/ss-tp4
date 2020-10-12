package engine.integrators;

import engine.Particle;
import engine.Vector;

import java.util.List;

public interface Forces {
    Vector getForces(Particle particle, Vector position, Vector velocity, List<Particle> particles);
}
