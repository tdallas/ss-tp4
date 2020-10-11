package planets.cutCondition;

import planets.Planet;

import java.util.List;

public interface PlanetCutCondition {
    boolean isFinished(List<Planet> planets, double time);
}
