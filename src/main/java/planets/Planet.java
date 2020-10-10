package planets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class Planet {
    private final int id;
    private PlanetVector position;
    private PlanetVector velocity;
    private final double mass;
    private final double radius;
    private final double redColor;
    private final double greenColor;
    private final double blueColor;
    private final double animationRadius;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equals(id, planet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
