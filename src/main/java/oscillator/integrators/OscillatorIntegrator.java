package oscillator.integrators;

import lombok.Getter;
import oscillator.OscillatorParticle;

@Getter
public abstract class OscillatorIntegrator {
    private final double springConstant;
    private final double viscosity;

    public OscillatorIntegrator(double springConstant, double viscosity) {
        this.springConstant = springConstant;
        this.viscosity = viscosity;
    }

    double getForces(double position, double velocity) {
        return -1 * position * springConstant - (viscosity * velocity);
    }

    public abstract void applyIntegrator(OscillatorParticle oscillatorParticle, double timeDelta);
}
