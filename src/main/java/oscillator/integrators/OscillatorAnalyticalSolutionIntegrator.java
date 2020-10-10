package oscillator.integrators;

import oscillator.OscillatorParticle;

//Solucion analitica
public class OscillatorAnalyticalSolutionIntegrator extends OscillatorIntegrator {
    private double time;
    private final double amplitude;

    public OscillatorAnalyticalSolutionIntegrator(double springConstant, double viscosity, double amplitude) {
        super(springConstant, viscosity);
        this.time = 0;
        this.amplitude = amplitude;
    }

    @Override
    public void applyIntegrator(OscillatorParticle oscillatorParticle, double timeDelta) {
        time += timeDelta;
        oscillatorParticle.setPosition(amplitude * Math.exp(-1 * getViscosity() / (2 * oscillatorParticle.getMass()) * time) * Math.cos(Math.pow(getSpringConstant() / oscillatorParticle.getMass() - getViscosity() * getViscosity() / (4 * oscillatorParticle.getMass() * oscillatorParticle.getMass()), 0.5) * time));
    }
}
