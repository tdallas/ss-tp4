import org.apache.commons.cli.*;
import systems.oscillator.OscillatorSystem;
import systems.planets.PlanetSystem;

public class Main {
    private static int system;

    public static void main(String[] args) {
        //parseArguments(args);

        OscillatorSystem.runOscillators();
        PlanetSystem.runPlanets();
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
