package oscillator.integrators;

import oscillator.OscillatorParticle;

public class OscillatorBeemanIntegrator extends OscillatorIntegrator{
    private boolean previousAccelerationSet;
    private double previousAcceleration;

    public OscillatorBeemanIntegrator() {
        this.previousAccelerationSet = false;
    }

    public void applyIntegrator(OscillatorParticle oscillatorParticle, double deltaT, double elasticConstant, double damping) {
        double forces = getForces(elasticConstant, damping, oscillatorParticle.getPosition(), oscillatorParticle.getVelocity());
        if(!previousAccelerationSet){
            previousAccelerationSet = true;
            double previousPosition = oscillatorParticle.getPosition() - oscillatorParticle.getVelocity() * deltaT + (1 / 2.0) * deltaT * deltaT * forces;
            double previousVelocity = oscillatorParticle.getVelocity() - forces * deltaT;
            double previousForces = getForces(elasticConstant, damping, previousPosition, previousVelocity);
            previousAcceleration = previousForces / oscillatorParticle.getMass();
        }
        double acceleration = forces / oscillatorParticle.getMass();
        oscillatorParticle.setPosition(oscillatorParticle.getPosition() + oscillatorParticle.getVelocity() * deltaT + (2/3.0) * acceleration * deltaT * deltaT - (1/6.0)  * previousAcceleration * deltaT * deltaT);
        double velocityPrediction = oscillatorParticle.getVelocity() + (3/2.0) * acceleration * deltaT - (1/2.0) * previousAcceleration * deltaT;
        double newForces = getForces(elasticConstant, damping, oscillatorParticle.getPosition(), velocityPrediction);
        double newAcceleration = newForces / oscillatorParticle.getMass();
        oscillatorParticle.setVelocity(oscillatorParticle.getVelocity() + (1/3.0) * newAcceleration * deltaT + (5/6.0) * acceleration * deltaT - (1/6.0) * previousAcceleration * deltaT);
        previousAcceleration = acceleration;
    }
}
