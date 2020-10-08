import engine.CutCondition;
import engine.EventDrivenSimulation;
import org.apache.commons.cli.*;
import system.CsvFileGenerator;
import system.EquilibriumCutCondition;
import system.GasesLawEquilibriumCutCondition;
import system.SystemGenerator;

import java.util.Random;

public class Main {
    private static int numberOfParticles;
    private static String filename;
    private static Long seed = null;
    private static double deltaTime;


    public static void main(String[] args) {
        long time;
        parseArguments(args);

        Random random;
        if (seed == null) {
            random = new Random();
            seed = random.nextLong();
            random.setSeed(seed);
        } else {
            random = new Random(seed);
        }

        //Print input
        System.out.println("--------------------------");
        System.out.println("Input: ");
        System.out.println("Seed: " + seed);
        System.out.println("Time delta: " + deltaTime);
        System.out.println("--------------------------");

        SystemGenerator systemGenerator;
        CutCondition equilibriumCutCondition;
        EventDrivenSimulation eventDrivenSimulation;

    }

    private static void parseArguments(String[] args) {
        Options options = new Options();

        Option outputOption = new Option("o", "output", true, "output file name");
        outputOption.setRequired(true);
        options.addOption(outputOption);

        Option deltaTimeOption = new Option("dt", "time-delta", true, "time delta for animations");
        deltaTimeOption.setRequired(true);
        options.addOption(deltaTimeOption);

        Option seedOption = new Option("s", "seed", true, "seed for randomizer (optional)");
        seedOption.setRequired(false);
        options.addOption(seedOption);

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

        filename = cmd.getOptionValue("output");
        if (filename.equals("walls")) {
            System.out.println("Invalid filename, cannot be named: walls");
            System.exit(1);
        }

        String aux = cmd.getOptionValue("seed");
        if (aux != null) {
            try {
                seed = Long.parseLong(aux);
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument seed, must be long");
                System.exit(1);
            }
        }

    }
}
