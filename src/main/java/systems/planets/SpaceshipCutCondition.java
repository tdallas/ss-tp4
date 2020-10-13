package systems.planets;

import engine.Particle;
import engine.cutCondition.CutCondition;

import java.util.List;

public class SpaceshipCutCondition implements CutCondition {
    private Double lastDistance;

    public SpaceshipCutCondition() {
        this.lastDistance = null;
    }

    @Override
    public boolean isFinished(List<Particle> particles, double time) {
        Particle mars = particles.get(2);
        Particle spaceship = particles.get(3);
        double distance = mars.getPosition().distance(spaceship.getPosition());
        if (lastDistance == null) {
            lastDistance = distance;
            return false;
        }

        if (lastDistance < distance) {
            System.out.println("Best distance: " + lastDistance);
            return true;
        }
        lastDistance = distance;
        return false;
    }
}
