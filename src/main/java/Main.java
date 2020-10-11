import org.apache.commons.cli.*;
import oscillator.OscillatorFileGenerator;
import oscillator.OscillatorParticle;
import oscillator.OscillatorSimulator;
import oscillator.cutCondition.OscillatorCutCondition;
import oscillator.cutCondition.OscillatorTimeCutCondition;
import oscillator.integrators.*;
import planets.PlanetFileGenerator;
import planets.PlanetSimulator;
import planets.PlanetSystemGenerator;
import planets.cutCondition.PlanetCutCondition;
import planets.cutCondition.PlanetTimeCutCondition;
import planets.integrators.PlanetBeemanIntegrator;
import planets.integrators.PlanetIntegrator;

public class Main {
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

        //simulateOscillator();
        simulatePlanets();
    }

    private static void simulatePlanets() {

//        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
//        PlanetIntegrator planetIntegrator = new PlanetVerletIntegrator(planetSystemGenerator.getGravitationalConstant());
//        PlanetFileGenerator planetFileGenerator = new PlanetFileGenerator("planet-verlet");
//        PlanetCutCondition planetCutCondition = new PlanetTimeCutCondition(31536000*10);
//        PlanetSimulator planetSimulator = new PlanetSimulator(900, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getPlanets());
//        planetSimulator.simulate();

        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
        PlanetIntegrator planetIntegrator = new PlanetBeemanIntegrator(planetSystemGenerator.getGravitationalConstant());
        PlanetFileGenerator planetFileGenerator = new PlanetFileGenerator("planet-beeman");
        PlanetCutCondition planetCutCondition = new PlanetTimeCutCondition(31536000 * 10);
        PlanetSimulator planetSimulator = new PlanetSimulator(900, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getPlanets());
        planetSimulator.simulate();

//        PlanetSystemGenerator planetSystemGenerator = new PlanetSystemGenerator();
//        PlanetIntegrator planetIntegrator = new PlanetGearIntegrator(planetSystemGenerator.getGravitationalConstant(), planetSystemGenerator.getPlanets());
//        PlanetFileGenerator planetFileGenerator = new PlanetFileGenerator("planet-gear");
//        PlanetCutCondition planetCutCondition = new PlanetTimeCutCondition(31536000 * 10);
//        PlanetSimulator planetSimulator = new PlanetSimulator(900, 86400, planetCutCondition, planetIntegrator, planetFileGenerator, planetSystemGenerator.getPlanets());
//        planetSimulator.simulate();
    }

    private static void simulateOscillator() {
        //Solucion analitica
        OscillatorParticle oscillatorParticle = new OscillatorParticle(1, 0, 70);
        OscillatorIntegrator oscillatorIntegrator = new OscillatorAnalyticalSolutionIntegrator(Math.pow(10, 4), 100, 1);
        OscillatorFileGenerator oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-analytic");
        OscillatorCutCondition oscillatorCutCondition = new OscillatorTimeCutCondition(5);
        OscillatorSimulator oscillatorSimulator = new OscillatorSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, oscillatorParticle);
        oscillatorSimulator.simulate();

        //Solucion VERLET
        oscillatorParticle = new OscillatorParticle(1, 0, 70);
        oscillatorIntegrator = new OscillatorVerletIntegrator(Math.pow(10, 4), 100);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-verlet");
        oscillatorCutCondition = new OscillatorTimeCutCondition(5);
        oscillatorSimulator = new OscillatorSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, oscillatorParticle);
        oscillatorSimulator.simulate();

        //Solucion BEEMAN
        oscillatorParticle = new OscillatorParticle(1, 0, 70);
        oscillatorIntegrator = new OscillatorBeemanIntegrator(Math.pow(10, 4), 100);
        oscillatorFileGenerator = new OscillatorFileGenerator("oscillator-beeman");
        oscillatorCutCondition = new OscillatorTimeCutCondition(5);
        oscillatorSimulator = new OscillatorSimulator(0.0001, 0.01, oscillatorCutCondition, oscillatorIntegrator, oscillatorFileGenerator, oscillatorParticle);
        oscillatorSimulator.simulate();

        //Solucion GEAR
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
