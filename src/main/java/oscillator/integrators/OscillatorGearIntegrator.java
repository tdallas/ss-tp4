package oscillator.integrators;

import org.apache.commons.math3.util.CombinatoricsUtils;
import oscillator.OscillatorParticle;

public class OscillatorGearIntegrator extends OscillatorIntegrator {
    private double[] previousPredictions;

    private static final double[] corrector = {3.0 / 16, 251.0 / 360, 1.0, 11.0 / 18, 1.0 / 6, 1.0 / 60};

    public OscillatorGearIntegrator(double springConstant, double viscosity) {
        super(springConstant, viscosity);
        previousPredictions = null;
    }

    @Override
    public void applyIntegrator(OscillatorParticle oscillatorParticle, double timeDelta) {
        if (this.previousPredictions == null) {
            this.previousPredictions = new double[6];
            this.previousPredictions[0] = oscillatorParticle.getPosition();
            this.previousPredictions[1] = oscillatorParticle.getVelocity();
            this.previousPredictions[2] = getForces(oscillatorParticle.getPosition(), oscillatorParticle.getVelocity()) / oscillatorParticle.getMass();
            this.previousPredictions[3] = 0;
            this.previousPredictions[4] = 0;
            this.previousPredictions[5] = 0;
        }

        // predict
        double firstDegree = timeDelta;
        double secondDegree = Math.pow(timeDelta, 2) / 2;
        double thirdDegree = Math.pow(timeDelta, 3) / 6;
        double fourthDegree = Math.pow(timeDelta, 4) / 24;
        double fifthDegree = Math.pow(timeDelta, 5) / 120;

        double[] predictions = new double[6];

        predictions[0] = previousPredictions[0]
                + previousPredictions[1] * firstDegree
                + previousPredictions[2] * secondDegree
                + previousPredictions[3] * thirdDegree
                + previousPredictions[4] * fourthDegree
                + previousPredictions[5] * fifthDegree;

        predictions[1] = previousPredictions[1]
                + previousPredictions[2] * firstDegree
                + previousPredictions[3] * secondDegree
                + previousPredictions[4] * thirdDegree
                + previousPredictions[5] * fourthDegree;

        predictions[2] = previousPredictions[2]
                + previousPredictions[3] * firstDegree
                + previousPredictions[4] * secondDegree
                + previousPredictions[5] * thirdDegree;

        predictions[3] = previousPredictions[3]
                + previousPredictions[4] * firstDegree
                + previousPredictions[5] * secondDegree;

        predictions[4] = previousPredictions[4]
                + previousPredictions[5] * firstDegree;

        predictions[5] = previousPredictions[5];

        // evaluate
        double deltaA = getForces(predictions[0], predictions[1]) / oscillatorParticle.getMass() - predictions[2];
        double deltaR2 = deltaA * timeDelta * timeDelta / 2;

        // correct
        for (int i = 0; i < 6; i++) {
            previousPredictions[i] = predictions[i] + corrector[i] * deltaR2 * CombinatoricsUtils.factorial(i) / Math.pow(timeDelta, i);
        }

        oscillatorParticle.setPosition(previousPredictions[0]);
        oscillatorParticle.setVelocity(previousPredictions[1]);
    }
}
