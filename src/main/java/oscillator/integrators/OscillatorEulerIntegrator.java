package oscillator.integrators;

import oscillator.OscillatorParticle;

public class OscillatorEulerIntegrator extends OscillatorIntegrator {
    public OscillatorEulerIntegrator(double springConstant, double viscosity) {
        super(springConstant, viscosity);
    }

    public void applyIntegrator(OscillatorParticle oscillatorParticle, double timeDelta) {
        double forces = getForces(oscillatorParticle.getPosition(), oscillatorParticle.getVelocity());
        oscillatorParticle.setVelocity(oscillatorParticle.getVelocity() + (timeDelta / oscillatorParticle.getMass()) * forces);
        oscillatorParticle.setPosition(oscillatorParticle.getPosition() + timeDelta * oscillatorParticle.getVelocity() + (timeDelta * timeDelta * forces) / (2 * oscillatorParticle.getMass()));
    }
}
