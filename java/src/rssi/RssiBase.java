package rssi;

import rssi.config.CsvMotesLoader;
import rssi.config.MotesLoader;

/**
 * Application's entry point.
  *
 */
public class RssiBase {

    public static void main(String[] args) throws Exception {

        if(args == null || args.length != 2) {
            printUsage();
            System.exit(-1);
        }


        MotesLoader motesLoader = new CsvMotesLoader();
  	    MotesControl motesControl = new MotesControl(motesLoader.loadMotes(args[0]));
  	    motesControl.start(args[1]);
    }

    private static void printUsage() {
        System.out.println("Usage: java RssiBase <motes_config.csv> <scenario.csv>");
        System.out.println("Example: java RssiBase ../config/motes.csv ../config/scenario.csv");
    }
}