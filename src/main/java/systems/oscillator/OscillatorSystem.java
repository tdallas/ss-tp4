package systems.oscillator;

import engine.FileGenerator;
import engine.Particle;
import engine.TimeStepSimulator;
import engine.Vector;
import engine.cutCondition.CutCondition;
import engine.cutCondition.TimeCutCondition;
import engine.integrators.*;

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

    public static void runOscillators() {
        //Solucion analitica
        Particle oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        Forces oscillatorForces = new OscillatorForces(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        Integrator oscillatorIntegrator = new OscillatorAnalyticalSolutionIntegrator(oscillatorForces, OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY, OSCILLATOR_AMPLITUDE);
        FileGenerator oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-analytic");
        CutCondition oscillatorCutCondition = new TimeCutCondition(5);
        TimeStepSimulator oscillatorSimulator = new TimeStepSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, Collections.singletonList(oscillatorParticle));
        oscillatorSimulator.simulate(true);

        //Integrador EULER MODIFICADO
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForces = new OscillatorForces(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        oscillatorIntegrator = new EulerIntegrator(oscillatorForces);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-euler");
        oscillatorCutCondition = new TimeCutCondition(5);
        oscillatorSimulator = new TimeStepSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, Collections.singletonList(oscillatorParticle));
        oscillatorSimulator.simulate(true);

        //Integrador BEEMAN
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForces = new OscillatorForces(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        oscillatorIntegrator = new BeemanIntegrator(oscillatorForces);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-beeman");
        oscillatorCutCondition = new TimeCutCondition(5);
        oscillatorSimulator = new TimeStepSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, Collections.singletonList(oscillatorParticle));
        oscillatorSimulator.simulate(true);

        //Integrador GEAR PREDICTOR CORRECTOR
        oscillatorParticle = new Particle(0, new Vector(OSCILLATOR_POSITION, 0), new Vector(OSCILLATOR_VELOCITY, 0), OSCILLATOR_MASS, 0, 0, 0, 0, 0, true);
        oscillatorForces = new OscillatorForces(OSCILLATOR_SPRING_CONSTANT, OSCILLATOR_VISCOSITY);
        List<Particle> particles = Collections.singletonList(oscillatorParticle);
        oscillatorIntegrator = new GearIntegrator(oscillatorForces, particles);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-gear");
        oscillatorCutCondition = new TimeCutCondition(5);
        oscillatorSimulator = new TimeStepSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, particles);
        oscillatorSimulator.simulate(true);
    }
}
