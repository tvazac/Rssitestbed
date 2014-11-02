package rssi.config;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CsvScenarioLoader implements ScenarioLoader {

	public List<Scenario> loadScenarios(String scenarioFile) {

		List<Scenario> scenarios = new ArrayList<>();

		BufferedReader scenarioBr;
		try {
			scenarioBr = new BufferedReader(new FileReader(scenarioFile));
		} catch (FileNotFoundException fileNotFound) {
        	System.err.println("File with motes not found: " + fileNotFound.getMessage());
        	return scenarios;
        }

        try {
            String scenarioLine;
            while ((scenarioLine = scenarioBr.readLine()) != null) {

                String[] tokens = scenarioLine.split(",");
                int packets = Integer.parseInt(tokens[0]);
                short power = (short) Integer.parseInt(tokens[1]);
                scenarios.add(new Scenario(packets, power));
            }
        } catch (IOException e) {
            System.err.println("Can not read from: " + scenarioFile + ". Reason: " + e.getMessage());
            System.exit(-1);
        }

        try {
			scenarioBr.close();
		} catch (IOException ex) {
			System.err.println("Can not close file: " + scenarioFile);
		}

		return scenarios;
	}

}