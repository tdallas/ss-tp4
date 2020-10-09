package oscillator.cutCondition;

public class OscillatorTimeCutCondition implements OscillatorCutCondition{
    private final double timeToCut;

    public OscillatorTimeCutCondition(double timeToCut) {
        this.timeToCut = timeToCut;
    }

    @Override
    public boolean isFinished(double time) {
        return time >= timeToCut;
    }
}
