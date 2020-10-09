package oscillator.integrators;

import oscillator.OscillatorParticle;

public class OscillatorVerletIntegrator extends OscillatorIntegrator{
    public void applyIntegrator(OscillatorParticle oscillatorParticle, double deltaT, double elasticConstant, double damping) {
        double forces = getForces(elasticConstant, damping, oscillatorParticle.getPosition(), oscillatorParticle.getVelocity());
        oscillatorParticle.setVelocity(oscillatorParticle.getVelocity() + (deltaT / oscillatorParticle.getMass()) * forces);
        oscillatorParticle.setPosition(oscillatorParticle.getPosition() + deltaT * oscillatorParticle.getVelocity() + deltaT * deltaT * forces / (2 * oscillatorParticle.getMass()));
    }
}
