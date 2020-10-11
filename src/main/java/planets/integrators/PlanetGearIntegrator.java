package planets.integrators;

import org.apache.commons.math3.util.CombinatoricsUtils;
import planets.Planet;
import planets.PlanetVector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetGearIntegrator extends PlanetIntegrator {
    private final Map<Planet, PlanetVector[]> previousPredictions;

    private static final double[] corrector = {3.0 / 16, 251.0 / 360, 1.0, 11.0 / 18, 1.0 / 6, 1.0 / 60};

    public PlanetGearIntegrator(double gravitationalConstant, List<Planet> planets) {
        super(gravitationalConstant);
        previousPredictions = new HashMap<>();
        for (Planet planet : planets) {
            PlanetVector[] previousPrediction = new PlanetVector[6];
            previousPrediction[0] = planet.getPosition();
            previousPrediction[1] = planet.getVelocity();
            previousPrediction[2] = getForces(planet, planet.getPosition(), planets).divide(planet.getMass());
            previousPrediction[3] = new PlanetVector(0, 0);
            previousPrediction[4] = new PlanetVector(0, 0);
            previousPrediction[5] = new PlanetVector(0, 0);
            previousPredictions.put(planet, previousPrediction);
        }
    }

    @Override
    public void applyIntegrator(double timeDelta, Planet planet, List<Planet> planets) {

        // predict
        double firstDegree = timeDelta;
        double secondDegree = Math.pow(timeDelta, 2) / 2;
        double thirdDegree = Math.pow(timeDelta, 3) / 6;
        double fourthDegree = Math.pow(timeDelta, 4) / 24;
        double fifthDegree = Math.pow(timeDelta, 5) / 120;

        PlanetVector[] previousPrediction = previousPredictions.get(planet);
        PlanetVector[] prediction = new PlanetVector[6];

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
        PlanetVector deltaA = getForces(planet, prediction[0], planets).divide(planet.getMass()).subtract(prediction[2]);
        PlanetVector deltaR2 = deltaA.multiply(timeDelta * timeDelta).divide(2);

        // correct
        for (int i = 0; i < 6; i++) {
            previousPrediction[i] = prediction[i].add(deltaR2.multiply(corrector[i] * CombinatoricsUtils.factorial(i) / Math.pow(timeDelta, i)));
        }

        planet.setPosition(previousPrediction[0]);
        planet.setVelocity(previousPrediction[1]);
    }
}
