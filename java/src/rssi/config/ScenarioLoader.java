package rssi.config;

import java.util.List;

/**
 * Loads test scenario from file and creates Scenario objects.
 */
public interface ScenarioLoader {
    /**
     * Parses given file with scenario, loads all rows and creates {@link Scenario} objects.
     *
     * @param scenarioFile Input file with scenario.
     * @return List of {@link Scenario}s.
     */
    public List<Scenario> loadScenarios(String scenarioFile);
}
