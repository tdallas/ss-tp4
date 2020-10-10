package planets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlanetVector {
    private final double x;
    private final double y;

    public PlanetVector add(PlanetVector other) {
        return new PlanetVector(x + other.x, y + other.y);
    }

    public PlanetVector sub(PlanetVector other) {
        return new PlanetVector(x - other.x, y - other.y);
    }

    public PlanetVector mul(double scalar) {
        return new PlanetVector(x * scalar, y * scalar);
    }

    public PlanetVector div(double scalar) {
        return new PlanetVector(x / scalar, y / scalar);
    }

    public double distance(PlanetVector other) {
        return Math.sqrt(distanceSquare(other));
    }

    public double distanceSquare(PlanetVector other) {
        return Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2);
    }
}
