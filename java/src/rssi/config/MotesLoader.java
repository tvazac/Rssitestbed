package rssi.config;

import net.tinyos.message.MoteIF;
import java.util.List;

/**
 * Loads motes and builds MoteIF objects.
 */
public interface MotesLoader {

    /**
     * Parses given file with motes, loads them and creates MoteIF objects.
     *
     * @param motesFile Input file with motes.
     * @return List of {@link MoteIF} motes.
     */
	public List<MoteIF> loadMotes(String motesFile);

}