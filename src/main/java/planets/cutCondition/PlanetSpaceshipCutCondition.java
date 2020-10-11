package planets.cutCondition;

import planets.Planet;

import java.util.List;

public class PlanetSpaceshipCutCondition implements PlanetCutCondition {
    private Double lastDistance;

    public PlanetSpaceshipCutCondition() {
        this.lastDistance = null;
    }

    @Override
    public boolean isFinished(List<Planet> planets, double time) {
        Planet mars = planets.get(2);
        Planet spaceship = planets.get(3);
        double distance = mars.getPosition().distance(spaceship.getPosition());
        if(lastDistance == null){
            lastDistance = distance;
            return false;
        }
        return lastDistance < distance;
    }
}
