package oscillator.integrators;

import oscillator.OscillatorParticle;

public class OscillatorBeemanIntegrator extends OscillatorIntegrator {
    private Double previousAcceleration;

    public OscillatorBeemanIntegrator(double springConstant, double viscosity) {
        super(springConstant, viscosity);
        this.previousAcceleration = null;
    }

    public void applyIntegrator(OscillatorParticle oscillatorParticle, double timeDelta) {
        double forces = getForces(oscillatorParticle.getPosition(), oscillatorParticle.getVelocity());
        if (previousAcceleration == null) {
            double previousPosition = oscillatorParticle.getPosition() - oscillatorParticle.getVelocity() * timeDelta + (1.0 / 2) * timeDelta * timeDelta * forces;
            double previousVelocity = oscillatorParticle.getVelocity() - forces * timeDelta;
            double previousForces = getForces(previousPosition, previousVelocity);
            previousAcceleration = previousForces / oscillatorParticle.getMass();
        }
        double acceleration = forces / oscillatorParticle.getMass();
        oscillatorParticle.setPosition(oscillatorParticle.getPosition() + oscillatorParticle.getVelocity() * timeDelta + (2.0 / 3) * acceleration * timeDelta * timeDelta - (1.0 / 6) * previousAcceleration * timeDelta * timeDelta);
        double velocityPrediction = oscillatorParticle.getVelocity() + (3.0 / 2) * acceleration * timeDelta - (1.0 / 2) * previousAcceleration * timeDelta;
        double newForces = getForces(oscillatorParticle.getPosition(), velocityPrediction);
        double newAcceleration = newForces / oscillatorParticle.getMass();
        oscillatorParticle.setVelocity(oscillatorParticle.getVelocity() + (1.0 / 3) * newAcceleration * timeDelta + (5.0 / 6) * acceleration * timeDelta - (1.0 / 6) * previousAcceleration * timeDelta);
        previousAcceleration = acceleration;
    }
}
