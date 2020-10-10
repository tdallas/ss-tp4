package planets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Vector {
    private final double x;
    private final double y;

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector sub(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    public Vector mul(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public double distance(Vector other) {
        return Math.sqrt(distanceSquare(other));
    }

    public double distanceSquare(Vector other) {
        return Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2);
    }
}
