package oscillator;

import oscillator.cutCondition.OscillatorCutCondition;
import oscillator.integrators.OscillatorIntegrator;

public class OscillatorSimulator {
    private final double timeDelta;
    private final double saveTimeDelta;
    private final OscillatorCutCondition oscillatorCutCondition;
    private final OscillatorIntegrator oscillatorIntegrator;
    private final OscillatorFileGenerator oscillatorFileGenerator;
    private final double elasticConstant;
    private final double damping;

    private final OscillatorParticle oscillatorParticle;
    private double time;
    private double timeToSave;

    public OscillatorSimulator(double timeDelta, double saveTimeDelta, OscillatorCutCondition oscillatorCutCondition, OscillatorIntegrator oscillatorIntegrator, OscillatorFileGenerator oscillatorFileGenerator, double elasticConstant, double damping, OscillatorParticle oscillatorParticle) {
        this.timeDelta = timeDelta;
        this.saveTimeDelta = saveTimeDelta;
        this.oscillatorCutCondition = oscillatorCutCondition;
        this.oscillatorIntegrator = oscillatorIntegrator;
        this.oscillatorFileGenerator = oscillatorFileGenerator;
        this.elasticConstant = elasticConstant;
        this.damping = damping;
        this.oscillatorParticle = oscillatorParticle;
        this.timeToSave = saveTimeDelta;
        this.time = 0;
    }

    public void simulate() {

        oscillatorFileGenerator.addToFile(oscillatorParticle, time);
        while (!oscillatorCutCondition.isFinished(time)) {
            oscillatorIntegrator.applyIntegrator(oscillatorParticle, timeDelta, elasticConstant, damping);
            time += timeDelta;

            if (time >= timeToSave) {
                oscillatorFileGenerator.addToFile(oscillatorParticle, time);
                timeToSave += saveTimeDelta;
            }
        }
        oscillatorFileGenerator.closeFile();
    }
}
