package oscillator.integrators;

import oscillator.OscillatorParticle;

//Solucion analitica
public class OscillatorSolutionIntegrator extends OscillatorIntegrator{
    private double time;
    private final double amplitude;

    public OscillatorSolutionIntegrator(double amplitude) {
        this.time = 0;
        this.amplitude = amplitude;
    }

    @Override
    public void applyIntegrator(OscillatorParticle oscillatorParticle, double deltaT, double elasticConstant, double damping) {
        time += deltaT;
        oscillatorParticle.setPosition(amplitude * Math.exp(-1 * damping / (2 * oscillatorParticle.getMass()) * time) * Math.cos(Math.pow(elasticConstant / oscillatorParticle.getMass() - damping * damping/(4 * oscillatorParticle.getMass() * oscillatorParticle.getMass()), 0.5) * time));
    }
}
