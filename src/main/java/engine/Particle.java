package engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class Particle {
    private final int id;
    private Vector position;
    private Vector velocity;
    private final double mass;
    private final double radius;
    private final double redColor;
    private final double greenColor;
    private final double blueColor;
    private final double animationRadius;
    private final boolean movableParticle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
