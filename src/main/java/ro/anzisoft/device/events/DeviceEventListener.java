package ro.anzisoft.device.events;

/**
 * <b>DeviceErrorListener</b><br>
 * 
 * @author omatei01 (2018)
 */
public interface DeviceEventListener extends java.util.EventListener {
	public void deviceStatusUpdateEventOccurred(DeviceStatusUpdateEvent e);
}
