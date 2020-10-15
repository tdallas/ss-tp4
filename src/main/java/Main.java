import org.apache.commons.cli.*;
import systems.oscillator.OscillatorSystem;
import systems.planets.JupiterSystem;
import systems.planets.MarsSystem;
import systems.planets.OrbitSystem;

public class Main {
    private static int system;

    public static void main(String[] args) {
        //parseArguments(args);

//        //EJ 1.1
//        OscillatorSystem.runOscillators();
//
//        //EJ 1.2 y 1.3
//
//        //EJ 2.1
//        MarsSystem.simulatePlanets(10);
//        MarsSystem.simulatePlanets(20);
//        MarsSystem.simulatePlanets(30);
//        MarsSystem.simulatePlanets(40);
//        MarsSystem.simulatePlanets(50);
//        MarsSystem.simulatePlanets(60);
//        MarsSystem.simulatePlanets(70);
//        MarsSystem.simulatePlanets(80);
//        MarsSystem.simulatePlanets(90);
//        MarsSystem.simulatePlanets(100);
//        MarsSystem.simulatePlanets(200);
//        MarsSystem.simulatePlanets(300);
//        MarsSystem.simulatePlanets(400);
//        MarsSystem.simulatePlanets(500);
//        MarsSystem.simulatePlanets(600);
//        MarsSystem.simulatePlanets(700);
//        MarsSystem.simulatePlanets(800);
//        MarsSystem.simulatePlanets(900);
//        MarsSystem.simulatePlanets(1000);
//
//        //EJ 2.2
//        MarsSystem.runSimulationBestDaySearch();
        MarsSystem.runSimulation();

        //EJ 2.3
//        JupiterSystem.runSimulationBestDaySearch();
        JupiterSystem.runSimulation();

        //More animations
//        OrbitSystem.runSimulation();

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
