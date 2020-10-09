package oscillator.integrators;

import oscillator.OscillatorParticle;

public abstract class OscillatorIntegrator {

    double getForces(double elasticConstant, double damping, double position, double velocity) {
        return -1 * position * elasticConstant - (damping * velocity);
    }

    public abstract void applyIntegrator(OscillatorParticle oscillatorParticle, double deltaT, double elasticConstant, double damping);
}
