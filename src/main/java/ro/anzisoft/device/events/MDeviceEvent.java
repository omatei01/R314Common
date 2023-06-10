package ro.anzisoft.device.events;

/**
 * <b>MDeviceEvent</b><br>
 * 
 * @author omatei01 (2018)
 */
public class MDeviceEvent extends java.util.EventObject {

	private static final long serialVersionUID = 890512894510553520L;

	public MDeviceEvent(Object source) {
		super(source);
		updateSequenceNumber();
		when = System.currentTimeMillis();
	}

	public synchronized final void updateSequenceNumber() {
		sequenceNumber = incrSequenceNumber();
	}

	public static final synchronized long incrSequenceNumber() {
		return ++globalSequenceNumber;
	}

	public final long getSequenceNumber() {
		return sequenceNumber;
	}

	public long getWhen() {
		return when;
	}

	protected long sequenceNumber;
	private static long globalSequenceNumber = 0;
	private long when;
}
