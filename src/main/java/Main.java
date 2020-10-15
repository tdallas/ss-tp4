import org.apache.commons.cli.*;
import systems.oscillator.OscillatorSystem;
import systems.planets.PlanetSystem;

public class Main {
    private static int system;

    public static void main(String[] args) {
        //parseArguments(args);

//        OscillatorSystem.runOscillators();
//        PlanetSystem.runPlanets();

        PlanetSystem.simulatePlanets(10);
        PlanetSystem.simulatePlanets(20);
        PlanetSystem.simulatePlanets(30);
        PlanetSystem.simulatePlanets(40);
        PlanetSystem.simulatePlanets(50);
        PlanetSystem.simulatePlanets(60);
        PlanetSystem.simulatePlanets(70);
        PlanetSystem.simulatePlanets(80);
        PlanetSystem.simulatePlanets(90);
        PlanetSystem.simulatePlanets(100);
        PlanetSystem.simulatePlanets(200);
        PlanetSystem.simulatePlanets(300);
        PlanetSystem.simulatePlanets(400);
        PlanetSystem.simulatePlanets(500);
        PlanetSystem.simulatePlanets(600);
        PlanetSystem.simulatePlanets(700);
        PlanetSystem.simulatePlanets(800);
        PlanetSystem.simulatePlanets(900);
        PlanetSystem.simulatePlanets(1000);
    }

    private static void parseArguments(String[] args) {
        Options options = new Options();

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
            system = Integer.parseInt(cmd.getOptionValue("system"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument system, must be integer between 1 and 5");
            System.exit(1);
        }
        if (system < 1 || system > 5) {
            System.out.println("Invalid argument system, must be integer between 1 and 5");
            System.exit(1);
        }
    }
}
