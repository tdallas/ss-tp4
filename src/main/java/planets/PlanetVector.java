package planets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlanetVector {
    private final double x;
    private final double y;

    public PlanetVector add(PlanetVector planetVector) {
        return new PlanetVector(this.x + planetVector.x, this.y + planetVector.y);
    }

    public PlanetVector subtract(PlanetVector planetVector) {
        return new PlanetVector(this.x - planetVector.x, this.y - planetVector.y);
    }

    public PlanetVector multiply(double number) {
        return new PlanetVector(this.x * number, this.y * number);
    }

    public PlanetVector divide(double number) {
        return new PlanetVector(this.x / number, this.y / number);
    }

    public double distance(PlanetVector planetVector) {
        return Math.hypot(this.x - planetVector.x, this.y - planetVector.y);
    }
}
