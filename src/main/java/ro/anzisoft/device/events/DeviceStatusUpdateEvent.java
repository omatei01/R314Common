package ro.anzisoft.device.events;

/**
 * <b>DeviceEvent</b><br>
 * 
 * @author omatei01 (2018)
 */
public class DeviceStatusUpdateEvent extends MDeviceEvent {
	private static final long serialVersionUID = 4821018630031774864L;

	public DeviceStatusUpdateEvent(Object source, int status) {
		super(source);

		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	protected int status;
}
