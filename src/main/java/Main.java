import org.apache.commons.cli.*;
import systems.planets.PlanetSystem;


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

//        OscillatorSystem.runOscillators();

        PlanetSystem.runPlanets();
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
