package oscillator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OscillatorParticle {
    private double position;
    private double velocity;
    private final double mass;

    public OscillatorParticle(double position, double velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

}
