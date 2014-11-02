package rssi;

import rssi.config.CsvScenarioLoader;
import rssi.config.Scenario;
import rssi.config.ScenarioLoader;
import rssi.db.Database;
import rssi.db.SQLiteDatabase;
import rssi.message.CommandMsg;
import rssi.message.RssiNoiseReportMsg;
import rssi.message.RssiReportMsg;
import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

/**
 *
 */
public class MotesControl implements MessageListener {

    private Database database;
    private ScenarioLoader scenarioLoader;

	private List<MoteIF> motes = new ArrayList<>();
    boolean dbReady = false;

	private static final int PACKET_FREQUENCY = 250;

    /**
     * Registers listeners to given motes, creates database and loads up the scenario.
     *
     * Active listeners are: {@link RssiReportMsg}, {@link RssiNoiseReportMsg}.
     *
     * @param motes Loaded motes.
     */
	public MotesControl(List<MoteIF> motes) {
		for(MoteIF mote : motes) {
			mote.registerListener(new RssiReportMsg(), this);
            mote.registerListener(new RssiNoiseReportMsg(), this);
			this.motes.add(mote);
		}

        database = new SQLiteDatabase();
        database.prepareDatabase();
        dbReady = true;

        scenarioLoader = new CsvScenarioLoader();
	}

    /**
     * Executes experiment.
     *
     * Each mote sends one packet and then transmitting is done by another mote. This is repeated n-times, where
     * n is equal to {@link Scenario.getPackets()#packets}
     *
     * @param scenarioFile File with scenario.
     */
    public void start(String scenarioFile) {
        CommandMsg cmdMsg = new CommandMsg();

        for(Scenario scenario : scenarioLoader.loadScenarios(scenarioFile)) {
            cmdMsg.set_power(scenario.getPower());
            cmdMsg.set_packets(1);
            System.out.println("Packets = " + scenario.getPackets() + " with power = " + scenario.getPower());
            for(int i = 0; i < scenario.getPackets(); i++) {
                for(MoteIF mote : motes) {
                    try {
                        mote.send(0, cmdMsg);
                    } catch (IOException e) {
                        System.err.println("Unable to send packet to mote: " + mote);
                    }
                    try { Thread.sleep(PACKET_FREQUENCY); }
                    catch (InterruptedException exception) {}
                }
            }

        }
    }

    @Override
	public void messageReceived(int to, Message message) {
        if(message instanceof RssiReportMsg) {
            RssiReportMsg msg = (RssiReportMsg) message;
            if(dbReady) {
                database.insertRssiRow(msg.get_nodeId(), msg.get_sourceId(), msg.get_rssi());
            }
            System.out.println("Node " + msg.get_nodeId() + " received packet from node: " + msg.get_sourceId() +
                    ", rssi = " + msg.get_rssi());
        } else {
            RssiNoiseReportMsg msg = (RssiNoiseReportMsg) message;
            if(dbReady) {
                database.insertRssiNoiseFloor(msg.get_nodeId(), msg.get_noise());
            }
            System.out.println("Noise from " + msg.get_nodeId() + " = " + msg.get_noise());
        }
  	}


}
