package planets.cutCondition;

public class PlanetTimeCutCondition implements PlanetCutCondition {
    private final double timeToCut;

    public PlanetTimeCutCondition(double timeToCut) {
        this.timeToCut = timeToCut;
    }

    @Override
    public boolean isFinished(double time) {
        return time >= timeToCut;
    }
}
