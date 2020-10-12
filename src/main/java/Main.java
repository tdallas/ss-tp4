import org.apache.commons.cli.*;
import oscillator.OscillatorFileGenerator;
import oscillator.OscillatorParticle;
import oscillator.OscillatorSimulator;
import oscillator.cutCondition.OscillatorCutCondition;
import oscillator.cutCondition.OscillatorTimeCutCondition;
import oscillator.integrators.*;
import planets.*;
import planets.cutCondition.PlanetCutCondition;
import planets.cutCondition.PlanetSpaceshipCutCondition;
import planets.cutCondition.PlanetTimeCutCondition;
import planets.integrators.PlanetBeemanIntegrator;
import planets.integrators.PlanetEulerIntegrator;
import planets.integrators.PlanetGearIntegrator;
import planets.integrators.PlanetIntegrator;

import java.util.List;

public class Main {
    private static final double SPACESHIP_MASS = 5 * Math.pow(10, 5);                    // kg
    private static final double SPACESHIP_RADIUS = 50;                                   // m
    private static final double SPACESHIP_HEIGHT = 1500 * Math.pow(10, 3);               // m
    private static final double SPACESHIP_ORBITAL_VELOCITY = 7.12 * Math.pow(10, 3);     // m/s
    private static final double SPACESHIP_LAUNCH_VELOCITY_0 = 8 * Math.pow(10, 3);       // m/s
    private static final double SPACESHIP_LAUNCH_VELOCITY_1 = 8 * Math.pow(10, 3);     // m/s

    private static int system;
    private static String filename;
    private static double deltaTime;
    private static double saveDeltaTime;

    public static void main(String[] args) {
        //parseArguments(args);

        //Print input
//        System.out.println("--------------------------");
//        System.out.println("Input: ");
//        System.out.println("Time delta: " + deltaTime);
//        System.out.println("Save time delta: " + saveDeltaTime);
//        System.out.println("File name: " + deltaTime);
//        System.out.println("--------------------------");

        //EJ 1.1
//        simulateOscillators();

        //EJ 2.1
//        simulatePlanets();

        //EJ 2.2
        //TARDA MUCHO PERO ENCUENTRA EL MEJOR DIA QUE SERIA 723 dias despues del 28/09/2020
//        int bestDayToSendSpaceshipV0 = searchBestDayToSendSpaceship(750, SPACESHIP_LAUNCH_VELOCITY_0, "v0", 0);
//        System.out.println("Best day: " + bestDayToSendSpaceshipV0);

        //Mejor dia es 723
        simulateSpaceShipToMars(723, SPACESHIP_LAUNCH_VELOCITY_0, "spaceship-to-mars-v0", 0);

        //EJ2.3
        //Tomamos V0 = 8 km/s pero cambiando el angulo de despegue en 77 grados
//        simulateSpaceShipToMarsWithDays(365, SPACESHIP_LAUNCH_VELOCITY_1, "spaceship-to-mars-v1", 77);

    }

    private static int searchBestDayToSendSpaceship(int days, double launch_velocity, String velocityString, double angleVariationInDegrees){
        int day;
        double bestDistance = Double.MAX_VALUE;
        int bestDay = 0;
        for(day = 1; day < days; day++){
            System.out.println("Day " + day);
            List<Planet> planets = simulateSpaceShipToMars(day, launch_velocity, "spaceship-" + day + "-day-" + velocityString, angleVariationInDegrees);
            Planet mars = planets.get(2);
            Planet spaceship = planets.get(3);
            double distance = mars.getPosition().distance(spaceship.getPosition());
            if(bestDistance > distance){
                bestDistance = distance;
                bestDay = day;
            }
        }
        return bestDay;
    }

    private static List<Planet> simulateSpaceShipToMars(double daysToSendSpaceship, double launch_velocity, String filename, double angleVariationInDegrees){
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        List<Planet> planets = planetSystemGenerator.getPlanets();
        //uso la misma lista

        PlanetIntegrator planetIntegrator = new PlanetGearIntegrator(planetSystemGenerator.getGravitationalConstant(), planetSystemGenerator.getPlanets());
        PlanetFileGenerator planetFileGenerator = new PlanetFileGenerator(filename);
        //dias hasta lanzar nave espacial
        PlanetCutCondition planetCutCondition = new PlanetTimeCutCondition(86400 * daysToSendSpaceship);
        PlanetSimulator planetSimulator = new PlanetSimulator(60, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planets);
        planetSimulator.simulate(false);

        //agrego nave y simulamos hasta condicion de nave espacial
        planetCutCondition = new PlanetSpaceshipCutCondition();
        addSpaceship(planets, launch_velocity, angleVariationInDegrees);
        planetIntegrator = new PlanetGearIntegrator(planetSystemGenerator.getGravitationalConstant(), planetSystemGenerator.getPlanets());
        planetSimulator = new PlanetSimulator(60, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planets);
        planetSimulator.simulate(true);
        return planets;
    }

    private static List<Planet> simulateSpaceShipToMarsWithDays(double days, double launch_velocity, String filename, double angleVariationInDegrees){
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        List<Planet> planets = planetSystemGenerator.getPlanets();
        addSpaceship(planets, launch_velocity, angleVariationInDegrees);
        PlanetIntegrator planetIntegrator = new PlanetGearIntegrator(planetSystemGenerator.getGravitationalConstant(), planetSystemGenerator.getPlanets());
        PlanetFileGenerator planetFileGenerator = new PlanetFileGenerator(filename);
        PlanetCutCondition planetCutCondition = new PlanetTimeCutCondition(86400 * days);
        PlanetSimulator planetSimulator = new PlanetSimulator(60, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planets);
        planetSimulator.simulate(true);
        return planets;
    }

    private static void addSpaceship(List<Planet> planets, double launch_velocity, double angleVariationInDegrees){
        Planet sun = planets.get(0);
        Planet earth = planets.get(1);
        double angle = Math.atan2(earth.getPosition().getX() - sun.getPosition().getX(), earth.getPosition().getY() - sun.getPosition().getY());
        angle = Math.toRadians(Math.toDegrees(angle) - angleVariationInDegrees);
        double distanceToEarthCenter = SPACESHIP_HEIGHT + earth.getRadius();
        double xPosition = earth.getPosition().getX() + Math.cos(angle) * distanceToEarthCenter;
        double yPosition = earth.getPosition().getY() + Math.sin(angle) * distanceToEarthCenter;
        double xVelocity = earth.getVelocity().getX() + Math.cos(angle) * (launch_velocity + SPACESHIP_ORBITAL_VELOCITY);
        double yVelocity = earth.getVelocity().getY() + Math.sin(angle) * (launch_velocity + SPACESHIP_ORBITAL_VELOCITY);
        double radius = SPACESHIP_RADIUS;
        double mass = SPACESHIP_MASS;
        planets.add(new Planet(3,
                new PlanetVector(xPosition, yPosition),
                new PlanetVector(xVelocity, yVelocity),
                mass, radius,
                1, 1, 1,
                radius * 100000000));
    }

    private static void simulatePlanets() {

        //Integrador EULER MODIFICADO
        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        PlanetIntegrator planetIntegrator = new PlanetEulerIntegrator(planetSystemGenerator.getGravitationalConstant());
        PlanetFileGenerator planetFileGenerator = new PlanetFileGenerator("planet-euler");
        PlanetCutCondition planetCutCondition = new PlanetTimeCutCondition(31536000);
        PlanetSimulator planetSimulator = new PlanetSimulator(60, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getPlanets());
        planetSimulator.simulate(true);

        //Integrador BEEMAN
        planetSystemGenerator = new PlanetSystemGenerator();
        planetIntegrator = new PlanetBeemanIntegrator(planetSystemGenerator.getGravitationalConstant());
        planetFileGenerator = new PlanetFileGenerator("planet-beeman");
        planetCutCondition = new PlanetTimeCutCondition(31536000);
        planetSimulator = new PlanetSimulator(60, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getPlanets());
        planetSimulator.simulate(true);

        //Integrador GEAR PREDICTOR CORRECTOR
        planetSystemGenerator = new PlanetSystemGenerator();
        planetIntegrator = new PlanetGearIntegrator(planetSystemGenerator.getGravitationalConstant(), planetSystemGenerator.getPlanets());
        planetFileGenerator = new PlanetFileGenerator("planet-gear");
        planetCutCondition = new PlanetTimeCutCondition(31536000);
        planetSimulator = new PlanetSimulator(60, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getPlanets());
        planetSimulator.simulate(true);
    }

    private static void simulateOscillators() {
        //Solucion analitica
        OscillatorParticle oscillatorParticle = new OscillatorParticle(1, 0, 70);
        OscillatorIntegrator oscillatorIntegrator = new OscillatorAnalyticalSolutionIntegrator(Math.pow(10, 4), 100, 1);
        OscillatorFileGenerator oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-analytic");
        OscillatorCutCondition oscillatorCutCondition = new OscillatorTimeCutCondition(5);
        OscillatorSimulator oscillatorSimulator = new OscillatorSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, oscillatorParticle);
        oscillatorSimulator.simulate();

        //Integrador EULER MODIFICADO
        oscillatorParticle = new OscillatorParticle(1, 0, 70);
        oscillatorIntegrator = new OscillatorEulerIntegrator(Math.pow(10, 4), 100);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-euler");
        oscillatorCutCondition = new OscillatorTimeCutCondition(5);
        oscillatorSimulator = new OscillatorSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, oscillatorParticle);
        oscillatorSimulator.simulate();

        //Integrador BEEMAN
        oscillatorParticle = new OscillatorParticle(1, 0, 70);
        oscillatorIntegrator = new OscillatorBeemanIntegrator(Math.pow(10, 4), 100);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-beeman");
        oscillatorCutCondition = new OscillatorTimeCutCondition(5);
        oscillatorSimulator = new OscillatorSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, oscillatorParticle);
        oscillatorSimulator.simulate();

        //Integrador GEAR PREDICTOR CORRECTOR
        oscillatorParticle = new OscillatorParticle(1, 0, 70);
        oscillatorIntegrator = new OscillatorGearIntegrator(Math.pow(10, 4), 100);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-gear");
        oscillatorCutCondition = new OscillatorTimeCutCondition(5);
        oscillatorSimulator = new OscillatorSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, oscillatorParticle);
        oscillatorSimulator.simulate();
    }

    private static void parseArguments(String[] args) {
        Options options = new Options();

        Option outputOption = new Option("o", "output", true, "output file name");
        outputOption.setRequired(true);
        options.addOption(outputOption);

        Option deltaTimeOption = new Option("dt", "time-delta", true, "time delta for animations");
        deltaTimeOption.setRequired(true);
        options.addOption(deltaTimeOption);

        Option saveDeltaTimeOption = new Option("sdt", "save-time-delta", true, "time delta for saving");
        saveDeltaTimeOption.setRequired(true);
        options.addOption(saveDeltaTimeOption);

        Option systemOption = new Option("s", "system", true, "system to simulate");
        systemOption.setRequired(true);
        options.addOption(systemOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar target/ss-tp4-1.0.jar", options);

            System.exit(1);
        }

        try {
            deltaTime = Double.parseDouble(cmd.getOptionValue("time-delta"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument number of particles, must be double");
            System.exit(1);
        }
        if (deltaTime < 0) {
            System.out.println("Invalid time delta, must be positive");
            System.exit(1);
        }

        try {
            saveDeltaTime = Double.parseDouble(cmd.getOptionValue("save-time-delta"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument number of particles, must be double");
            System.exit(1);
        }
        if (saveDeltaTime < 0) {
            System.out.println("Invalid save time delta, must be positive");
            System.exit(1);
        }

        try {
            system = Integer.parseInt(cmd.getOptionValue("system"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument system, must be integer between 1 and 5");
            System.exit(1);
        }
        if (system < 1 || system > 5) {
            System.out.println("Invalid argument system, must be integer between 1 and 5");
            System.exit(1);
        }

        filename = cmd.getOptionValue("output");
    }
}
