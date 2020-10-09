package oscillator.integrators;

import oscillator.OscillatorParticle;

public class OscillatorVerletIntegrator extends OscillatorIntegrator{
    public OscillatorVerletIntegrator(double spring, double viscosity) {
        super(spring, viscosity);
    }

    public void applyIntegrator(OscillatorParticle oscillatorParticle, double timeDelta) {
        double forces = getForces(oscillatorParticle.getPosition(), oscillatorParticle.getVelocity());
        oscillatorParticle.setVelocity(oscillatorParticle.getVelocity() + (timeDelta / oscillatorParticle.getMass()) * forces);
        oscillatorParticle.setPosition(oscillatorParticle.getPosition() + timeDelta * oscillatorParticle.getVelocity() + timeDelta * timeDelta * forces / (2 * oscillatorParticle.getMass()));
    }
}
