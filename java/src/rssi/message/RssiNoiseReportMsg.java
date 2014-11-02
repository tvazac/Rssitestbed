package rssi.message;

/**
 * This class is automatically generated by mig. DO NOT EDIT THIS FILE.
 * This class implements a Java interface to the 'rssi.message.RssiNoiseReportMsg'
 * rssi.message type.
 */

public class RssiNoiseReportMsg extends net.tinyos.message.Message {

    /** The default size of this rssi.message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 3;

    /** The Active Message type associated with this rssi.message. */
    public static final int AM_TYPE = 4;

    /** Create a new rssi.message.RssiNoiseReportMsg of size 3. */
    public RssiNoiseReportMsg() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new rssi.message.RssiNoiseReportMsg of the given data_length. */
    public RssiNoiseReportMsg(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new rssi.message.RssiNoiseReportMsg with the given data_length
     * and base offset.
     */
    public RssiNoiseReportMsg(int data_length, int base_offset) {
        super(data_length, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new rssi.message.RssiNoiseReportMsg using the given byte array
     * as backing store.
     */
    public RssiNoiseReportMsg(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new rssi.message.RssiNoiseReportMsg using the given byte array
     * as backing store, with the given base offset.
     */
    public RssiNoiseReportMsg(byte[] data, int base_offset) {
        super(data, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new rssi.message.RssiNoiseReportMsg using the given byte array
     * as backing store, with the given base offset and data length.
     */
    public RssiNoiseReportMsg(byte[] data, int base_offset, int data_length) {
        super(data, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new rssi.message.RssiNoiseReportMsg embedded in the given rssi.message
     * at the given base offset.
     */
    public RssiNoiseReportMsg(net.tinyos.message.Message msg, int base_offset) {
        super(msg, base_offset, DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new rssi.message.RssiNoiseReportMsg embedded in the given rssi.message
     * at the given base offset and length.
     */
    public RssiNoiseReportMsg(net.tinyos.message.Message msg, int base_offset, int data_length) {
        super(msg, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this rssi.message. Includes the
     * rssi.message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <rssi.message.RssiNoiseReportMsg> \n";
      try {
        s += "  [nodeId=0x"+Long.toHexString(get_nodeId())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [noise=0x"+Long.toHexString(get_noise())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      return s;
    }

    // Message-type-specific access methods appear below.

    /////////////////////////////////////////////////////////
    // Accessor methods for field: nodeId
    //   Field type: short, unsigned
    //   Offset (bits): 0
    //   Size (bits): 8
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'nodeId' is signed (false).
     */
    public static boolean isSigned_nodeId() {
        return false;
    }

    /**
     * Return whether the field 'nodeId' is an array (false).
     */
    public static boolean isArray_nodeId() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'nodeId'
     */
    public static int offset_nodeId() {
        return (0 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'nodeId'
     */
    public static int offsetBits_nodeId() {
        return 0;
    }

    /**
     * Return the value (as a short) of the field 'nodeId'
     */
    public short get_nodeId() {
        return (short)getUIntBEElement(offsetBits_nodeId(), 8);
    }

    /**
     * Set the value of the field 'nodeId'
     */
    public void set_nodeId(short value) {
        setUIntBEElement(offsetBits_nodeId(), 8, value);
    }

    /**
     * Return the size, in bytes, of the field 'nodeId'
     */
    public static int size_nodeId() {
        return (8 / 8);
    }

    /**
     * Return the size, in bits, of the field 'nodeId'
     */
    public static int sizeBits_nodeId() {
        return 8;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: noise
    //   Field type: int, unsigned
    //   Offset (bits): 8
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'noise' is signed (false).
     */
    public static boolean isSigned_noise() {
        return false;
    }

    /**
     * Return whether the field 'noise' is an array (false).
     */
    public static boolean isArray_noise() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'noise'
     */
    public static int offset_noise() {
        return (8 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'noise'
     */
    public static int offsetBits_noise() {
        return 8;
    }

    /**
     * Return the value (as a int) of the field 'noise'
     */
    public int get_noise() {
        return (int)getUIntBEElement(offsetBits_noise(), 16);
    }

    /**
     * Set the value of the field 'noise'
     */
    public void set_noise(int value) {
        setUIntBEElement(offsetBits_noise(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'noise'
     */
    public static int size_noise() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'noise'
     */
    public static int sizeBits_noise() {
        return 16;
    }

}
