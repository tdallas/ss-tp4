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

    private static final double TIME_DELTA = 60;                                         // 1 minuto en segundos
    private static final double SAVE_TIME_DELTA = 86400;                                 // 1 dia en segundos

    public static void runPlanets() {

        //EJ 2.1
//        simulatePlanets();

        //EJ 2.2
        //TARDA MUCHO PERO ENCUENTRA EL MEJOR DIA QUE SERIA 723 dias despues del 28/09/2020
//        int bestDayToSendSpaceshipV0 = searchBestDayToSendSpaceship(750, SPACESHIP_LAUNCH_VELOCITY_0, "v0", 0);
//        System.out.println("Best day: " + bestDayToSendSpaceshipV0);

//        int bestHourToSendSpaceshipV0 = searchBestHourOfDayToSendSpaceship(723, SPACESHIP_LAUNCH_VELOCITY_0, "v0", 0);
//        System.out.println("Best hour: " + bestHourToSendSpaceshipV0);

//        int bestMinuteToSendSpaceshipV0 = searchBestMinuteOfHourOfDayToSendSpaceship(723, 11, SPACESHIP_LAUNCH_VELOCITY_0, "v0", 0);
//        System.out.println("Best minute: " + bestMinuteToSendSpaceshipV0);

        //Mejor dia es 723 21/09/2022
        simulateSpaceShipToMars(742, SPACESHIP_LAUNCH_VELOCITY_0, "0-spaceship-to-mars", 0);
        //simulateSpaceShipToMarsWithDays(730, SPACESHIP_LAUNCH_VELOCITY_0, "0-spaceship-to-mars", 0);

        //EJ2.3
        //Tomamos V0 = 8 km/s pero cambiando el angulo de despegue en 77 grados
//        simulateSpaceShipToMarsWithDays(365, SPACESHIP_LAUNCH_VELOCITY_1, "1-spaceship-to-mars", 77);

    }

    private static int searchBestHourOfDayToSendSpaceship(int day, double launch_velocity, String velocityString, double angleVariationInDegrees) {
        double bestDistance = Double.MAX_VALUE;

        int hour, bestHour = 0;
        for (hour = 0; hour < 24; hour++) {
            System.out.println("Hour " + hour);
            List<Particle> particles = simulateSpaceShipToMars(day, hour, launch_velocity, "spaceship-" + day + "-day-" + hour + "-hour-"  + velocityString, angleVariationInDegrees);
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

    private static int searchBestMinuteOfHourOfDayToSendSpaceship(int day, int hour, double launch_velocity, String velocityString, double angleVariationInDegrees) {
        double bestDistance = Double.MAX_VALUE;

        int minute, bestMinute = 0;
        for (minute = 0; minute < 60; minute++) {
            System.out.println("Minute " + minute);
            List<Particle> particles = simulateSpaceShipToMars(day, hour, minute, launch_velocity, "spaceship-" + day + "-day-" + hour + "-hour-" + minute + "-minute-" + velocityString, angleVariationInDegrees);
            Particle mars = particles.get(2);
            Particle spaceship = particles.get(3);
            double distance = mars.getPosition().distance(spaceship.getPosition());
            if (bestDistance > distance) {
                bestDistance = distance;
                bestMinute = minute;
            }
        }
        return bestMinute;
    }

    private static int searchBestDayAndHourToSendSpaceship(int days, double launch_velocity, String velocityString, double angleVariationInDegrees) {
        int day;
        double bestDistance = Double.MAX_VALUE;
        int bestDay = 0;
        for (day = 1; day < days; day++) {
            System.out.println("Day " + day);
            List<Particle> particles = simulateSpaceShipToMars(day, launch_velocity, "spaceship-" + day + "-day-" + velocityString, angleVariationInDegrees);
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

    private static int searchBestDayToSendSpaceship(int days, double launch_velocity, String velocityString, double angleVariationInDegrees) {
        int day;
        double bestDistance = Double.MAX_VALUE;
        int bestDay = 0;
        for (day = 1; day < days; day++) {
            System.out.println("Day " + day);
            List<Particle> particles = simulateSpaceShipToMars(day, launch_velocity, "spaceship-" + day + "-day-" + velocityString, angleVariationInDegrees);
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

    private static List<Particle> simulateSpaceShipToMars(int daysToSendSpaceship, int hoursToSendSpaceship, int minutesToSendSpaceship, double launch_velocity, String filename, double angleVariationInDegrees) {
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        List<Particle> particles = planetSystemGenerator.getParticles();
        //uso la misma lista

        ForcesCalculator planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        Integrator planetIntegrator = new GearIntegrator(planetForcesCalculator, particles);
        FileGenerator planetFileGenerator = new PlanetFileGenerator(filename);
        //dias hasta lanzar nave espacial
        CutCondition planetCutCondition = new TimeCutCondition(86400 * daysToSendSpaceship + 3600 * hoursToSendSpaceship + 60 * minutesToSendSpaceship);
        TimeStepSimulator planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(false);

        //agrego nave y simulamos hasta condicion de nave espacial
        planetCutCondition = new SpaceshipCutCondition();
        addSpaceship(particles, launch_velocity, angleVariationInDegrees);
        planetIntegrator = new GearIntegrator(planetForcesCalculator, planetSystemGenerator.getParticles());
        planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(true);
        return particles;
    }

    private static List<Particle> simulateSpaceShipToMars(int daysToSendSpaceship, int hoursToSendSpaceship, double launch_velocity, String filename, double angleVariationInDegrees) {
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        List<Particle> particles = planetSystemGenerator.getParticles();
        //uso la misma lista

        ForcesCalculator planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        Integrator planetIntegrator = new GearIntegrator(planetForcesCalculator, particles);
        FileGenerator planetFileGenerator = new PlanetFileGenerator(filename);
        //dias hasta lanzar nave espacial
        CutCondition planetCutCondition = new TimeCutCondition(86400 * daysToSendSpaceship + 3600 * hoursToSendSpaceship);
        TimeStepSimulator planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(false);

        //agrego nave y simulamos hasta condicion de nave espacial
        planetCutCondition = new SpaceshipCutCondition();
        addSpaceship(particles, launch_velocity, angleVariationInDegrees);
        planetIntegrator = new GearIntegrator(planetForcesCalculator, planetSystemGenerator.getParticles());
        planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(true);
        return particles;
    }

    private static List<Particle> simulateSpaceShipToMars(int daysToSendSpaceship, double launch_velocity, String filename, double angleVariationInDegrees) {
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        List<Particle> particles = planetSystemGenerator.getParticles();
        //uso la misma lista

        ForcesCalculator planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        Integrator planetIntegrator = new GearIntegrator(planetForcesCalculator, particles);
        FileGenerator planetFileGenerator = new PlanetFileGenerator(filename);
        //dias hasta lanzar nave espacial
        CutCondition planetCutCondition = new TimeCutCondition(86400 * daysToSendSpaceship);
        TimeStepSimulator planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(false);

        //agrego nave y simulamos hasta condicion de nave espacial
        planetCutCondition = new SpaceshipCutCondition();
        addSpaceship(particles, launch_velocity, angleVariationInDegrees);
        planetIntegrator = new GearIntegrator(planetForcesCalculator, planetSystemGenerator.getParticles());
        planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(true);
        return particles;
    }

    private static List<Particle> simulateSpaceShipToMarsWithDays(int days, double launch_velocity, String filename, double angleVariationInDegrees) {
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        List<Particle> particles = planetSystemGenerator.getParticles();
        addSpaceship(particles, launch_velocity, angleVariationInDegrees);
        ForcesCalculator planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        Integrator planetIntegrator = new GearIntegrator(planetForcesCalculator, particles);
        FileGenerator planetFileGenerator = new PlanetFileGenerator(filename);
        CutCondition planetCutCondition = new TimeCutCondition(86400 * days);
        TimeStepSimulator planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, particles);
        planetSimulator.simulate(true);
        return particles;
    }

    private static void addSpaceship(List<Particle> particles, double launchVelocity, double angleVariationInDegrees) {
        Particle earth = particles.get(1);
        double alpha = Math.atan2(earth.getPosition().getY(), earth.getPosition().getX());
        double beta = Math.atan2(earth.getPosition().getX(), earth.getPosition().getY());
        double launchAngle = Math.toRadians(Math.toDegrees(beta) - angleVariationInDegrees);
        double xPosition = earth.getPosition().getX() + Math.cos(alpha) * (SPACESHIP_HEIGHT_FROM_EARTH + earth.getRadius());
        double yPosition = earth.getPosition().getY() + Math.sin(alpha) * (SPACESHIP_HEIGHT_FROM_EARTH + earth.getRadius());
        double xVelocity = earth.getVelocity().getX() + Math.cos(launchAngle) * (SPACESHIP_ORBITAL_VELOCITY + launchVelocity);
        double yVelocity = earth.getVelocity().getY() + Math.sin(launchAngle) * (SPACESHIP_ORBITAL_VELOCITY + launchVelocity);
        double radius = SPACESHIP_RADIUS;
        double mass = SPACESHIP_MASS;
        particles.add(new Particle(3,
                new Vector(xPosition, yPosition),
                new Vector(xVelocity, yVelocity),
                mass, radius,
                1, 1, 1,
                radius * 100000000,
                true));
    }

    private static void simulatePlanets() {

        //Integrador EULER MODIFICADO
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        ForcesCalculator planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        Integrator planetIntegrator = new EulerIntegrator(planetForcesCalculator);
        FileGenerator planetFileGenerator = new PlanetFileGenerator("planet-euler");
        CutCondition planetCutCondition = new TimeCutCondition(31536000);
        TimeStepSimulator planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getParticles());
        planetSimulator.simulate(true);

        //Integrador BEEMAN
        planetSystemGenerator = new PlanetSystemGenerator();
        planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        planetIntegrator = new BeemanIntegrator(planetForcesCalculator, 60, planetSystemGenerator.getParticles());
        planetFileGenerator = new PlanetFileGenerator("planet-beeman");
        planetCutCondition = new TimeCutCondition(31536000);
        planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getParticles());
        planetSimulator.simulate(true);

        //Integrador GEAR PREDICTOR CORRECTOR
        planetSystemGenerator = new PlanetSystemGenerator();
        planetForcesCalculator = new PlanetForcesCalculator(planetSystemGenerator.getGravitationalConstant());
        planetIntegrator = new GearIntegrator(planetForcesCalculator, planetSystemGenerator.getParticles());
        planetFileGenerator = new PlanetFileGenerator("planet-gear");
        planetCutCondition = new TimeCutCondition(31536000);
        planetSimulator = new TimeStepSimulator(TIME_DELTA, SAVE_TIME_DELTA, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getParticles());
        planetSimulator.simulate(true);
    }
}
