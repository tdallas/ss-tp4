package systems.oscillator;

import engine.*;
import engine.cutCondition.CutCondition;
import engine.cutCondition.TimeCutCondition;
import engine.integrators.BeemanIntegrator;
import engine.integrators.EulerIntegrator;
import engine.integrators.GearIntegrator;
import engine.integrators.Integrator;

import java.util.Collections;
import java.util.List;

public class OscillatorSystem {
    private static final double OSCILLATOR_POSITION = 1;
    private static final double OSCILLATOR_VELOCITY = 0;
    private static final double OSCILLATOR_MASS = 70;
    private static final double OSCILLATOR_SPRING_CONSTANT = Math.pow(10, 4);
    private static final double OSCILLATOR_VISCOSITY = 100;
    private static final double OSCILLATOR_AMPLITUDE = 1;
    private static final double OSCILLATOR_CUTOFF_TIME = 5;

    private static final double TIME_DELTA = 0.0001;
    private static final double SAVE_TIME_DELTA = 0.01;

    public static void runOscillators() {
        //Solucion analitica
        Particle oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        ForcesCalculator oscillatorForcesCalculator = new OscillatorForcesCalculator(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        Integrator oscillatorIntegrator = new OscillatorAnalyticalSolutionIntegrator(oscillatorForcesCalculator, OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY, OSCILLATOR_AMPLITUDE);
        FileGenerator oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-analytic");
        CutCondition oscillatorCutCondition = new TimeCutCondition(OSCILLATOR_CUTOFF_TIME);
        TimeStepSimulator oscillatorSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, Collections.singletonList(oscillatorParticle));
        oscillatorSimulator.simulate(true);

        //Integrador EULER MODIFICADO
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForcesCalculator = new OscillatorForcesCalculator(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        oscillatorIntegrator = new EulerIntegrator(oscillatorForcesCalculator);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-euler");
        oscillatorCutCondition = new TimeCutCondition(OSCILLATOR_CUTOFF_TIME);
        oscillatorSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, Collections.singletonList(oscillatorParticle));
        oscillatorSimulator.simulate(true);

        //Integrador BEEMAN
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForcesCalculator = new OscillatorForcesCalculator(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        List<Particle> particles = Collections.singletonList(oscillatorParticle);
        oscillatorIntegrator = new BeemanIntegrator(oscillatorForcesCalculator, TIME_DELTA, particles);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-beeman");
        oscillatorCutCondition = new TimeCutCondition(OSCILLATOR_CUTOFF_TIME);
        oscillatorSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, particles);
        oscillatorSimulator.simulate(true);

        //Integrador GEAR PREDICTOR CORRECTOR
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForcesCalculator = new OscillatorForcesCalculator(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        particles = Collections.singletonList(oscillatorParticle);
        oscillatorIntegrator = new GearIntegrator(oscillatorForcesCalculator, particles);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-gear");
        oscillatorCutCondition = new TimeCutCondition(OSCILLATOR_CUTOFF_TIME);
        oscillatorSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, particles);
        oscillatorSimulator.simulate(true);
    }

    public static void runOscillators(double timeDelta) {
        //Solucion analitica
        Particle oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        ForcesCalculator oscillatorForcesCalculator = new OscillatorForcesCalculator(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        Integrator oscillatorIntegrator = new OscillatorAnalyticalSolutionIntegrator(oscillatorForcesCalculator, OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY, OSCILLATOR_AMPLITUDE);
        FileGenerator oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-analytic");
        CutCondition oscillatorCutCondition = new TimeCutCondition(OSCILLATOR_CUTOFF_TIME);
        TimeStepSimulator oscillatorSimulator = new TimeStepSimulator(timeDelta, SAVE_TIME_DELTA, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, Collections.singletonList(oscillatorParticle));
        oscillatorSimulator.simulate(true);

        //Integrador EULER MODIFICADO
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForcesCalculator = new OscillatorForcesCalculator(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        oscillatorIntegrator = new EulerIntegrator(oscillatorForcesCalculator);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-euler");
        oscillatorCutCondition = new TimeCutCondition(OSCILLATOR_CUTOFF_TIME);
        oscillatorSimulator = new TimeStepSimulator(timeDelta, SAVE_TIME_DELTA, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, Collections.singletonList(oscillatorParticle));
        oscillatorSimulator.simulate(true);

        //Integrador BEEMAN
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForcesCalculator = new OscillatorForcesCalculator(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        List<Particle> particles = Collections.singletonList(oscillatorParticle);
        oscillatorIntegrator = new BeemanIntegrator(oscillatorForcesCalculator, timeDelta, particles);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-beeman");
        oscillatorCutCondition = new TimeCutCondition(OSCILLATOR_CUTOFF_TIME);
        oscillatorSimulator = new TimeStepSimulator(timeDelta, SAVE_TIME_DELTA, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, particles);
        oscillatorSimulator.simulate(true);

        //Integrador GEAR PREDICTOR CORRECTOR
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForcesCalculator = new OscillatorForcesCalculator(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        particles = Collections.singletonList(oscillatorParticle);
        oscillatorIntegrator = new GearIntegrator(oscillatorForcesCalculator, particles);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-gear");
        oscillatorCutCondition = new TimeCutCondition(OSCILLATOR_CUTOFF_TIME);
        oscillatorSimulator = new TimeStepSimulator(timeDelta, SAVE_TIME_DELTA, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, particles);
        oscillatorSimulator.simulate(true);
    }
}
