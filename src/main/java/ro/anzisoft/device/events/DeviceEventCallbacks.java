package ro.anzisoft.device.events;

import ro.anzisoft.device.services.MBaseService;

/**
 * <b>DeviceEventCallbacks</b><br>
 * 
 * @author omatei01 (2018)
 */
public interface DeviceEventCallbacks {
	void fireDeviceStatusUpdateEvent(DeviceStatusUpdateEvent e);

	MBaseService getEventSource2();
}
