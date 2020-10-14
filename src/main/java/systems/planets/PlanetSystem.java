package systems.planets;

import engine.*;
import engine.cutCondition.CutCondition;
import engine.cutCondition.TimeCutCondition;
import engine.integrators.BeemanIntegrator;
import engine.integrators.EulerIntegrator;
import engine.integrators.GearIntegrator;
import engine.integrators.Integrator;

import java.util.List;

public class PlanetSystem {
    private static final double SPACESHIP_MASS = 5 * Math.pow(10, 5);                    // kg
    private static final double SPACESHIP_RADIUS = 50;                                   // m
    private static final double SPACESHIP_HEIGHT_FROM_EARTH = 1500 * Math.pow(10, 3);    // m
    private static final double SPACESHIP_ORBITAL_VELOCITY = 7.12 * Math.pow(10, 3);     // m/s
    private static final double SPACESHIP_LAUNCH_VELOCITY_0 = 8 * Math.pow(10, 3);       // m/s
    private static final double SPACESHIP_LAUNCH_VELOCITY_1 = 8 * Math.pow(10, 3);       // m/s
    private static final double SPACESHIP_ANIMATION_RADIUS = SPACESHIP_RADIUS * 100000000;

    private static final double TIME_DELTA = 60;                                         // 1 minuto en segundos
    private static final double SAVE_TIME_DELTA = 86400;                                 // 1 dia en segundos

    public static void runPlanets() {

        //EJ 2.1
//        simulatePlanets();

        //EJ 2.2
        //TARDA MUCHO PERO ENCUENTRA EL MEJOR DIA QUE SERIA 723 dias despues del 28/09/2020
//        int bestDayToSendSpaceshipV0 = searchBestDayToSendSpaceship(750, SPACESHIP_LAUNCH_VELOCITY_0, "v0", 0);
//        System.out.println("Best day: " + bestDayToSendSpaceshipV0);

//        int bestHourToSendSpaceshipV0 = searchBestHourToSendSpaceship(711, SPACESHIP_LAUNCH_VELOCITY_0, "v0", 0);
//        System.out.println("Best hour: " + bestHourToSendSpaceshipV0);

        //Mejor dia es 711 a las 14 horas 9/9/2022
        simulateSpaceShipToMars(711,14, SPACESHIP_LAUNCH_VELOCITY_0, "0-spaceship-to-mars", 0, 50);

        //EJ2.3
        //Tomamos V0 = 8 km/s pero cambiando el angulo de despegue en 77 grados
//        simulateSpaceShipToMarsWithDays(365, SPACESHIP_LAUNCH_VELOCITY_1, "1-spaceship-to-mars", 77);

    }

    private static int searchBestDayToSendSpaceship(int days, double launchVelocity, String velocityString, double angleVariationInDegrees) {
        int day;
        double bestDistance = Double.MAX_VALUE;
        int bestDay = 0;
        for (day = 0; day < days + 1; day++) {
            System.out.println("Day " + day);
            List<Particle> particles = simulateSpaceShipToMars(day,0, launchVelocity, "spaceship-" + day + "-day-" + velocityString, angleVariationInDegrees, 0);
            Particle mars = particles.get(2);
            Particle spaceship = particles.get(3);
            double distance = mars.getPosition().distance(spaceship.getPosition());
            if (bestDistance > distance) {
                bestDistance = distance;
                bestDay = day;
            }
        }
        return bestDay;
    }

    private static int searchBestHourToSendSpaceship(int day, double launchVelocity, String velocityString, double angleVariationInDegrees) {
        int hour;
        double bestDistance = Double.MAX_VALUE;
        int bestHour = 0;
        for (hour = 0; hour < 24; hour++) {
            System.out.println("Hour " + hour);
            List<Particle> particles = simulateSpaceShipToMars(day, hour, launchVelocity, "spaceship-" + day + "-day-" + hour + "-hour-" + velocityString, angleVariationInDegrees, 0);
            Particle mars = particles.get(2);
            Particle spaceship = particles.get(3);
            double distance = mars.getPosition().distance(spaceship.getPosition());
            if (bestDistance > distance) {
                bestDistance = distance;
                bestHour = hour;
            }
        }
        return bestHour;
    }

    private static List<Particle> simulateSpaceShipToMars(int day, int hour, double launchVelocity, String filename, double angleVariationInDegrees, int daysAfterMarsOrbit) {
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        List<Particle> particles = planetSystemGenerator.getParticles();
        //uso la misma lista

        ForcesCalculator planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        Integrator planetIntegrator = new GearIntegrator(planetForcesCalculator, particles);
        FileGenerator planetFileGenerator = new PlanetFileGenerator(filename);
        //dias hasta lanzar nave espacial
        CutCondition planetCutCondition = new TimeCutCondition(86400 * day + 3600 * hour);
        TimeStepSimulator planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(false);
        double time = planetSimulator.getTime();

        //agrego nave y simulamos hasta condicion de nave espacial
        planetCutCondition = new SpaceshipCutCondition(planetFileGenerator);
        addSpaceship(particles, launchVelocity, angleVariationInDegrees);
        planetIntegrator = new GearIntegrator(planetForcesCalculator, planetSystemGenerator.getParticles());
        planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.setTime(time);
        if(daysAfterMarsOrbit < 1) {
            planetSimulator.simulate(true);
        }
        else{
            planetSimulator.simulate(false);
            time = planetSimulator.getTime();
            planetCutCondition = new TimeCutCondition(86400 * daysAfterMarsOrbit + time);
            planetIntegrator = new GearIntegrator(planetForcesCalculator, planetSystemGenerator.getParticles());
            planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
            planetSimulator.setTime(time);
            planetSimulator.simulate(true);
        }
        return particles;
    }

    private static List<Particle> simulateSpaceShipToMarsWithDays(int days, double launchVelocity, String filename, double launchAngleVariationInDegrees) {
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        List<Particle> particles = planetSystemGenerator.getParticles();
        addSpaceship(particles, launchVelocity, launchAngleVariationInDegrees);
        ForcesCalculator planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        Integrator planetIntegrator = new GearIntegrator(planetForcesCalculator, particles);
        FileGenerator planetFileGenerator = new PlanetFileGenerator(filename);
        CutCondition planetCutCondition = new TimeCutCondition(86400 * days);
        TimeStepSimulator planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(true);
        return particles;
    }

    private static void addSpaceship(List<Particle> particles, double launchVelocity, double launchAngleVariation) {
        Particle earth = particles.get(1);

        double positionAngle = Math.atan2(earth.getPosition().getY(), earth.getPosition().getX());
        double orbitalVelocityAngle = Math.atan2(earth.getPosition().getX(), earth.getPosition().getY());
        double launchAngle = Math.toRadians(Math.toDegrees(orbitalVelocityAngle) + launchAngleVariation);

        double xPosition = Math.abs((SPACESHIP_HEIGHT_FROM_EARTH + earth.getRadius()) * Math.cos(positionAngle));
        double yPosition = Math.abs((SPACESHIP_HEIGHT_FROM_EARTH + earth.getRadius()) * Math.sin(positionAngle));
        double xVelocity = Math.abs(SPACESHIP_ORBITAL_VELOCITY * Math.cos(orbitalVelocityAngle))
                + Math.abs(launchVelocity * Math.cos(launchAngle));
        double yVelocity = Math.abs(SPACESHIP_ORBITAL_VELOCITY * Math.sin(orbitalVelocityAngle))
                + Math.abs(launchVelocity * Math.sin(launchAngle));;

        xPosition = earth.getPosition().getX() + Math.signum(earth.getPosition().getX()) * xPosition;
        yPosition = earth.getPosition().getY() + Math.signum(earth.getPosition().getY()) * yPosition;
        xVelocity = earth.getVelocity().getX() + Math.signum(earth.getVelocity().getX()) * xVelocity;
        yVelocity = earth.getVelocity().getY() + Math.signum(earth.getVelocity().getY()) * yVelocity;

        particles.add(new Particle(3,
                new Vector(xPosition, yPosition),
                new Vector(xVelocity, yVelocity),
                SPACESHIP_MASS, SPACESHIP_RADIUS,
                1, 1, 1,
                SPACESHIP_ANIMATION_RADIUS,
                true));
    }

    private static void simulatePlanets() {

        //Integrador EULER MODIFICADO
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        ForcesCalculator planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        Integrator planetIntegrator = new EulerIntegrator(planetForcesCalculator);
        FileGenerator planetFileGenerator = new PlanetFileGenerator("planet-euler");
        CutCondition planetCutCondition = new TimeCutCondition(86400 * 365);
        TimeStepSimulator planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getParticles());
        planetSimulator.simulate(true);

        //Integrador BEEMAN
        planetSystemGenerator = new PlanetSystemGenerator();
        planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        planetIntegrator = new BeemanIntegrator(planetForcesCalculator, TIME_DELTA, planetSystemGenerator.getParticles());
        planetFileGenerator = new PlanetFileGenerator("planet-beeman");
        planetCutCondition = new TimeCutCondition(86400 * 365);
        planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getParticles());
        planetSimulator.simulate(true);

        //Integrador GEAR PREDICTOR CORRECTOR
        planetSystemGenerator = new PlanetSystemGenerator();
        planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        planetIntegrator = new GearIntegrator(planetForcesCalculator, planetSystemGenerator.getParticles());
        planetFileGenerator = new PlanetFileGenerator("planet-gear");
        planetCutCondition = new TimeCutCondition(86400 * 365);
        planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getParticles());
        planetSimulator.simulate(true);
    }
}
