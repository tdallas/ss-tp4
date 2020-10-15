package engine.integrators;

import engine.ForcesCalculator;
import engine.Particle;
import engine.Vector;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GearIntegrator extends Integrator {
    private final Map<Particle, Vector[]> previousPredictions;
    private boolean withVelocities;

    private static final double[] correctorWithVelocities = {3.0 / 16, 251.0 / 360, 1.0, 11.0 / 18, 1.0 / 6, 1.0 / 60};
    private static final double[] correctorWithoutVelocities = {3.0 / 20, 251.0 / 360, 1.0, 11.0 / 18, 1.0 / 6, 1.0 / 60};

    public GearIntegrator(ForcesCalculator forcesCalculator, List<Particle> particles, boolean withVelocities) {
        super(forcesCalculator);
        this.withVelocities = withVelocities;
        previousPredictions = new HashMap<>();
        for (Particle particle : particles) {
            Vector[] previousPrediction = new Vector[6];
            previousPrediction[0] = particle.getPosition();
            previousPrediction[1] = particle.getVelocity();
            previousPrediction[2] = getForces(particle, particle.getPosition(), particle.getVelocity(), particles).divide(particle.getMass());
            previousPrediction[3] = new Vector(0, 0);
            previousPrediction[4] = new Vector(0, 0);
            previousPrediction[5] = new Vector(0, 0);
            previousPredictions.put(particle, previousPrediction);
        }
    }

    @Override
    public void applyIntegrator(double timeDelta, Particle particle, List<Particle> particles) {

        // predict
        double firstDegree = timeDelta;
        double secondDegree = Math.pow(timeDelta, 2) / 2;
        double thirdDegree = Math.pow(timeDelta, 3) / 6;
        double fourthDegree = Math.pow(timeDelta, 4) / 24;
        double fifthDegree = Math.pow(timeDelta, 5) / 120;

        Vector[] previousPrediction = previousPredictions.get(particle);
        Vector[] prediction = new Vector[6];

        prediction[0] = previousPrediction[0].add(
                previousPrediction[1].multiply(firstDegree)).add(
                previousPrediction[2].multiply(secondDegree)).add(
                previousPrediction[3].multiply(thirdDegree)).add(
                previousPrediction[4].multiply(fourthDegree)).add(
                previousPrediction[5].multiply(fifthDegree));

        prediction[1] = previousPrediction[1].add(
                previousPrediction[2].multiply(firstDegree)).add(
                previousPrediction[3].multiply(secondDegree)).add(
                previousPrediction[4].multiply(thirdDegree)).add(
                previousPrediction[5].multiply(fourthDegree));

        prediction[2] = previousPrediction[2].add(
                previousPrediction[3].multiply(firstDegree)).add(
                previousPrediction[4].multiply(secondDegree)).add(
                previousPrediction[5].multiply(thirdDegree));

        prediction[3] = previousPrediction[3].add(
                previousPrediction[4].multiply(firstDegree)).add(
                previousPrediction[5].multiply(secondDegree));

        prediction[4] = previousPrediction[4].add(
                previousPrediction[5].multiply(firstDegree));

        prediction[5] = previousPrediction[5];

        // evaluate
        Vector deltaA = getForces(particle, prediction[0], prediction[1], particles).divide(particle.getMass()).subtract(prediction[2]);
        Vector deltaR2 = deltaA.multiply(timeDelta * timeDelta).divide(2);

        // correct
        if(withVelocities) {
            for (int i = 0; i < 6; i++) {
                previousPrediction[i] = prediction[i].add(deltaR2.multiply(correctorWithVelocities[i] * CombinatoricsUtils.factorial(i) / Math.pow(timeDelta, i)));
            }
        }
        else{
            for (int i = 0; i < 6; i++) {
                previousPrediction[i] = prediction[i].add(deltaR2.multiply(correctorWithoutVelocities[i] * CombinatoricsUtils.factorial(i) / Math.pow(timeDelta, i)));
            }
        }

        particle.setPosition(previousPrediction[0]);
        particle.setVelocity(previousPrediction[1]);
    }
}
