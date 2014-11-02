package rssi.db;

/**
 * Database interface provides method for creating database and inserting operations.
 */
public interface Database {

    /**
     * Creates database and tables (if necessary)
     */
    public void prepareDatabase();

    /**
     * Inserts new row into Rssi table.
     *
     * @param moteId ID of receiving mote.
     * @param sourceId ID of transmitting mote.
     * @param rssi Measured rssi value.
     */
    public void insertRssiRow(int moteId, int sourceId, int rssi);

    /**
     * Inserts new row into RssiNoiseFloor table.
     * @param moteId ID of measuring mote.
     * @param rssiNoiseFloor Measured Rssi noise floor value.
     */
    public void insertRssiNoiseFloor(int moteId, int rssiNoiseFloor);

}
