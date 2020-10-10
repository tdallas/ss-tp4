package oscillator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OscillatorParticle {
    private double position;
    private double velocity;
    private final double mass;
}
