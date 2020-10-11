package planets.cutCondition;

import planets.Planet;

import java.util.List;

public class PlanetTimeCutCondition implements PlanetCutCondition {
    private final double timeToCut;

    public PlanetTimeCutCondition(double timeToCut) {
        this.timeToCut = timeToCut;
    }

    @Override
    public boolean isFinished(List<Planet> planets, double time) {
        return time >= timeToCut;
    }
}
