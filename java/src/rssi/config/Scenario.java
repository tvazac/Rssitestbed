package rssi.config;

public class Scenario {

	private int packets;
	private short power;

	public Scenario(int packets, short power) {
		this.packets = packets;
		this.power = power;
	}

	public int getPackets() {
		return packets;
	}

	public short getPower() {
		return power;
	}

}