package oscillator.integrators;

import lombok.Getter;
import oscillator.OscillatorParticle;

@Getter
public abstract class OscillatorIntegrator {
    private final double spring;
    private final double viscosity;

    public OscillatorIntegrator(double spring, double viscosity) {
        this.spring = spring;
        this.viscosity = viscosity;
    }

    double getForces(double position, double velocity) {
        return -1 * position * spring - (viscosity * velocity);
    }

    public abstract void applyIntegrator(OscillatorParticle oscillatorParticle, double timeDelta);
}
